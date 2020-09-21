package com.chentongwei.service.doutu.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.chentongwei.entity.doutu.io.PictureUpvoteListIO;
import com.chentongwei.entity.doutu.vo.PictureUpvoteListVO;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chentongwei.cache.redis.IZSetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.doutu.IPictureDAO;
import com.chentongwei.dao.doutu.IPictureUpvoteDAO;
import com.chentongwei.entity.doutu.io.PictureUpvoteIO;
import com.chentongwei.entity.doutu.vo.PictureDetailVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import com.chentongwei.service.doutu.IPictureUpvoteService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 点赞业务层接口实现
 * 
 * @author TongWei.Chen 2017-5-30 18:21:36
 */
@Service
public class PictureUpvoteServiceImpl implements IPictureUpvoteService {

	private static final Logger LOG = LoggerFactory.getLogger(PictureUpvoteServiceImpl.class);

	@Autowired
	private IPictureUpvoteDAO pictureUpvoteDAO;
	@Autowired
	private IPictureDAO pictureDAO;
	@Autowired
	private IZSetCache zsetCache;

	@Override
	public List<PictureUpvoteListVO> listByUserID(PictureUpvoteListIO pictureUpvoteListIO) {
		PageHelper.startPage(pictureUpvoteListIO.getPageNum(), pictureUpvoteListIO.getPageSize());
		return pictureUpvoteDAO.listByUserID(pictureUpvoteListIO);
	}

	@Transactional
	@Override
	public boolean save(PictureUpvoteIO pictureUpvote) {
		//查看图片是否存在
		PictureDetailVO picture = pictureDAO.getByID(pictureUpvote.getPictureId());
		CommonExceptionHandler.nullCheck(picture);
		//查看是否已经点赞过该图片
		int count = pictureUpvoteDAO.findByUserIdAndPicId(pictureUpvote.getUserId(), pictureUpvote.getPictureId());
		CommonExceptionHandler.existsCheck(count, ResponseEnum.ALREADY_UPVOTE);
		//进行保存
		boolean flag = pictureUpvoteDAO.save(pictureUpvote);
		CommonExceptionHandler.flagCheck(flag);
		//redis的zset + 1
		Set<Object> set = zsetCache.getZSet(RedisEnum.PICTURE.getKey() + picture.getCatalogId());
		Iterator<Object> iterator = set.iterator();
		while (iterator.hasNext()) {
			Object object = iterator.next();
			PictureListVO pictureVO = (PictureListVO) object;
			if(Objects.equals(pictureVO.getId(), pictureUpvote.getPictureId())) {
				zsetCache.remove(RedisEnum.PICTURE.getKey() + picture.getCatalogId(), pictureVO);
				pictureVO.setCount(pictureVO.getCount() + 1);
				zsetCache.cacheZSet(RedisEnum.PICTURE.getKey() + picture.getCatalogId(), pictureVO, 0L);
				return true;
			}
		}
		LOG.info("用户{}点赞图片成功", pictureUpvote.getUserId().toString());
		return true;
	}

}
