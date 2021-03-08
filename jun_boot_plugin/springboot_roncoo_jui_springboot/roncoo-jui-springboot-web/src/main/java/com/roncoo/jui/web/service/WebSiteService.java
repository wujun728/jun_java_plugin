package com.roncoo.jui.web.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.roncoo.jui.common.dao.WebSiteDao;
import com.roncoo.jui.common.dao.WebSiteUrlDao;
import com.roncoo.jui.common.entity.WebSite;
import com.roncoo.jui.common.entity.WebSiteExample;
import com.roncoo.jui.common.entity.WebSiteExample.Criteria;
import com.roncoo.jui.common.entity.WebSiteUrl;
import com.roncoo.jui.common.entity.WebSiteUrlExample;
import com.roncoo.jui.common.util.ArrayListUtil;
import com.roncoo.jui.common.util.ConfUtil;
import com.roncoo.jui.common.util.PageUtil;
import com.roncoo.jui.common.util.SqlUtil;
import com.roncoo.jui.common.util.base.Base;
import com.roncoo.jui.common.util.jui.Page;
import com.roncoo.jui.web.bean.qo.WebSiteQO;
import com.roncoo.jui.web.bean.vo.WebSiteUrlVO;
import com.roncoo.jui.web.bean.vo.WebSiteVO;
import com.xiaoleilu.hutool.crypto.SecureUtil;
import com.xiaoleilu.hutool.util.ObjectUtil;

/**
 * 网址汇总
 *
 * @author Wujun
 * @since 2017-11-22
 */
@Component
public class WebSiteService extends Base {

	@Autowired
	private WebSiteDao dao;
	@Autowired
	private WebSiteUrlDao webSiteUrlDao;

	public Page<WebSiteVO> listForPage(int pageCurrent, int pageSize, WebSiteQO qo) {
		WebSiteExample example = new WebSiteExample();
		Criteria c = example.createCriteria();
		if (StringUtils.hasText(qo.getSiteName())) {
			c.andSiteNameLike(SqlUtil.like(qo.getSiteName()));
		}
		example.setOrderByClause(" status_id desc, sort desc, id desc ");
		Page<WebSite> page = dao.listForPage(pageCurrent, pageSize, example);
		return PageUtil.transform(page, WebSiteVO.class);
	}

	public int save(WebSiteQO qo, MultipartFile file) {
		if (!file.isEmpty()) {
			// 上传
			String fileName = file.getOriginalFilename();// 文件名
			String filePath = ConfUtil.FILEPATH;
			filePath = filePath + SecureUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf(".")); // 注意，linux下文件名为中文的情况
			logger.warn("当前上传的文件名为：{}，上传的目录位置：{}", fileName, filePath);
			File dest = new File(filePath);
			if (!dest.getParentFile().exists()) {
				// 判断文件父目录是否存在
				dest.getParentFile().mkdirs();
			}
			try {
				// 保存文件
				file.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			qo.setSiteLogo(dest.getName());
		}

		WebSite record = new WebSite();
		BeanUtils.copyProperties(qo, record);
		return dao.save(record);
	}

	public int deleteById(Long id) {
		return dao.deleteById(id);
	}

	public WebSiteVO getById(Long id) {
		WebSiteVO vo = new WebSiteVO();
		WebSite record = dao.getById(id);
		BeanUtils.copyProperties(record, vo);
		return vo;
	}

	public int updateById(WebSiteQO qo, MultipartFile file) {
		if (!file.isEmpty()) {
			// 上传
			String fileName = file.getOriginalFilename();// 文件名
			String filePath = ConfUtil.FILEPATH;
			filePath = filePath + SecureUtil.simpleUUID() + fileName.substring(fileName.lastIndexOf(".")); // 注意，linux下文件名为中文的情况
			logger.warn("当前上传的文件名为：{}，上传的目录位置：{}", fileName, filePath);
			File dest = new File(filePath);
			if (!dest.getParentFile().exists()) {
				// 判断文件父目录是否存在
				dest.getParentFile().mkdirs();
			}
			try {
				// 保存文件
				file.transferTo(dest);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			qo.setSiteLogo(dest.getName());
		}
		WebSite record = new WebSite();
		BeanUtils.copyProperties(qo, record);
		return dao.updateById(record);
	}

	public WebSiteVO main(Long id) {
		WebSiteVO vo = new WebSiteVO();
		WebSite webSite = dao.getById(id);
		if (ObjectUtil.isNotNull(webSite)) {
			WebSiteUrlExample example = new WebSiteUrlExample();
			example.createCriteria().andSiteIdEqualTo(webSite.getId());
			List<WebSiteUrl> list = webSiteUrlDao.listByExample(example);
			BeanUtils.copyProperties(webSite, vo);
			vo.setList(ArrayListUtil.copy(list, WebSiteUrlVO.class));
		}
		return vo;
	}

}
