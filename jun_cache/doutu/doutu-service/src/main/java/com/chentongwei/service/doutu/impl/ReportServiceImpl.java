package com.chentongwei.service.doutu.impl;

import com.chentongwei.cache.redis.IZSetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.constant.StatusEnum;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.exception.BussinessException;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.doutu.IPictureDAO;
import com.chentongwei.dao.doutu.IReportDAO;
import com.chentongwei.entity.doutu.io.ReportIO;
import com.chentongwei.entity.doutu.io.ReportStatusIO;
import com.chentongwei.entity.doutu.vo.PictureDetailVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import com.chentongwei.entity.doutu.vo.ReportVO;
import com.chentongwei.service.doutu.IReportService;
import com.chentongwei.util.QiniuUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 图片举报业务实现
 * 
 * @author TongWei.Chen 2017-5-22 20:07:54
 */
@Service
public class ReportServiceImpl implements IReportService {

	private static final Logger LOG = LoggerFactory.getLogger(ReportServiceImpl.class);

	@Autowired
	private IReportDAO reportDAO;
	@Autowired
	private IPictureDAO pictureDAO;
	@Autowired
	private IZSetCache zsetCache;
	@Autowired
	private QiniuUtil qiniuUtil;
	
	@Override
	public List<ReportVO> listWaitCheck(Page page) {
		//start page
		PageHelper.startPage(page.getPageNum(), page.getPageSize());
		return reportDAO.listByStatus(StatusEnum.REPORT_WAIT_CHECK_STATUS.getCode());
	}

	@Override
	public boolean save(ReportIO reportIO) {
		//检查所举报的图片是否存在
		PictureDetailVO pictureVO = pictureDAO.getByID(reportIO.getPictureId());
		CommonExceptionHandler.nullCheck(pictureVO);
		//检查是否举报过了
		int count = reportDAO.checkIsReport(reportIO.getPictureId(), reportIO.getUserId());
		CommonExceptionHandler.existsCheck(count, ResponseEnum.ALREADY_REPORT);
		//举报成功
		boolean flag = reportDAO.save(reportIO);
		CommonExceptionHandler.flagCheck(flag);
		LOG.info(reportIO.getUserId().toString(), "用户举报了图片");
		return true;
	}

	@Transactional
	@Override
	public boolean editStatus(ReportStatusIO reportStatusIO) {
		//审核时需要判断图片是否是待审核状态
		Integer status = reportDAO.checkReportStatus(reportStatusIO.getId());
		if(! Objects.equals(status, StatusEnum.REPORT_WAIT_CHECK_STATUS.getCode())) {
			throw new BussinessException(ResponseEnum.NOT_WAIT_CHECK);
		}
		//审核通过
		if(reportStatusIO.getStatus() == StatusEnum.REPORT_PASS_CHECK_STATUS.getCode()) {
			auditPass(reportStatusIO.getId());
		}
		
		boolean flag = reportDAO.editStatus(reportStatusIO);
		CommonExceptionHandler.flagCheck(flag);
		LOG.info("审核成功");
		return true;
	}

	/**
	 * 审核通过做的事
	 * 
	 * @param id：审核id
	 */
	private void auditPass(Long id) {
		LOG.info("审核通过");
		//拿到分类id---key
		Integer catalogId = reportDAO.getCatalogID(id);
		//找到需要移除的对象---value
		PictureListVO picture = pictureDAO.getByIDForCache(id);
		zsetCache.remove(RedisEnum.PICTURE.getKey() + catalogId, picture);
		LOG.info("审核通过，删除redis中对应的图片成功");
		//删除对应的表与关联表
		boolean pictureFlag = pictureDAO.delete(picture.getId());
		CommonExceptionHandler.flagCheck(pictureFlag);
		LOG.info("审核通过，删除db中对应的图片成功");
		//删除七牛云
		qiniuUtil.delete("doutu", picture.getUrl());
		LOG.info("审核通过，删除七牛云中对应的图片");
	}
}