package com.chentongwei.service.doutu.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chentongwei.cache.redis.IListCache;
import com.chentongwei.cache.redis.IZSetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.common.constant.ResponseEnum;
import com.chentongwei.common.constant.StatusEnum;
import com.chentongwei.common.entity.Page;
import com.chentongwei.common.handler.CommonExceptionHandler;
import com.chentongwei.dao.doutu.IPictureDAO;
import com.chentongwei.dao.doutu.IPictureTagDAO;
import com.chentongwei.entity.doutu.io.*;
import com.chentongwei.entity.doutu.vo.PictureListAdminVO;
import com.chentongwei.entity.doutu.vo.PictureListCacheVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import com.chentongwei.service.doutu.IPictureService;
import com.chentongwei.util.QETag;
import com.chentongwei.util.QiniuUtil;
import com.github.pagehelper.PageHelper;
import com.qiniu.common.QiniuException;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 图片业务层实现
 *
 * @author TongWei.Chen 2017-05-17 14:14:49
 */
@Service
public class PictureServiceImpl implements IPictureService {

    private static final Logger LOG = LoggerFactory.getLogger(PictureServiceImpl.class);

    @Autowired
    private IPictureDAO pictureDAO;
    @Autowired
    private IPictureTagDAO pictureTagDAO;
    @Autowired
    private QiniuUtil qiniuUtil;
    @Autowired
    private IZSetCache zsetCache;
    @Autowired
    private IListCache listCache;

    @Override
    public List<PictureListVO> listByCatalogId(PictureListCacheIO pictureListIO) {
        PageHelper.startPage(pictureListIO.getPageNum(), pictureListIO.getPageSize());
        return pictureDAO.listByCatalogId(pictureListIO.getCatalogId());
    }

    @Override
	public PictureListCacheVO listCache(PictureListCacheIO pictureListIO) {
    	PictureListCacheVO picture = new PictureListCacheVO();
        Map<String, Integer> map = getPage(pictureListIO.getPageSize(), pictureListIO.getPageNum());
		Set<Object> value = zsetCache.getZSet(RedisEnum.PICTURE.getKey() + pictureListIO.getCatalogId(), map.get("start"), map.get("end"));
		Iterator<Object> iterator = value.iterator();
		List<PictureListVO> list = new ArrayList<>();
		while(iterator.hasNext()) {
			Object obj = iterator.next();
			PictureListVO pictureVO = (PictureListVO) obj;
			list.add(pictureVO);
		}
		//count
		long count = zsetCache.zCard(RedisEnum.PICTURE.getKey() + pictureListIO.getCatalogId());
		//set
		picture.setTotal(count);
		picture.setList(list);
		return picture;
	}

    @Override
    public List<PictureListAdminVO> listPictureAdmin(PictureListAdminIO pictureListAdminIO) {
        PageHelper.startPage(pictureListAdminIO.getPageNum(), pictureListAdminIO.getPageSize());
        return pictureDAO.listPictureAdmin(pictureListAdminIO);
    }

    @Override
    public List<PictureListVO> listIndex(Page page) {
        PageHelper.startPage(page.getPageNum(), page.getPageSize());
        return pictureDAO.list();
    }

    @Deprecated
    public PictureListCacheVO listIndexBak(Page page) {
        PictureListCacheVO picture = new PictureListCacheVO();
        Map<String, Integer> map = getPage(page.getPageSize(), page.getPageNum());
        List<String> list = listCache.getList(RedisEnum.PICTURE.getKey() + RedisEnum.PICTURE_INDEX.getKey(), map.get("start"), map.get("end"));
        //返回的list
        List<PictureListVO> pictureList = new ArrayList<>();
        list.forEach(obj -> {
            pictureList.add(JSON.parseObject(obj, PictureListVO.class));
        });
        //count
        long count = listCache.size(RedisEnum.PICTURE.getKey() + RedisEnum.PICTURE_INDEX.getKey());
        //set
        picture.setTotal(count);
        picture.setList(pictureList);
        return picture;
    }

    @Override
    public int existsHash(String hex) {
        return pictureDAO.existsHash(hex);
    }

    @Override
    @Transactional
    public boolean save(PictureSaveIO pictureSaveIO) {
        PictureIO pictureIO = new PictureIO();
        appendSaveParam(pictureIO, pictureSaveIO);
        //检查图片是否为空
        CommonExceptionHandler.listCheck(pictureSaveIO.getPictures(), ResponseEnum.NOT_SELECT_PICTURE);
        //检查标签是否为空
        CommonExceptionHandler.listCheck(pictureSaveIO.getPictureTags(), ResponseEnum.NOT_SELECT_PICTURE);
        pictureSaveIO.getPictures().forEach(picture -> {
            pictureIO.setHash(picture.getHash());
            pictureIO.setUrl(picture.getUrl());
            pictureIO.setBytes(picture.getBytes());
            pictureIO.setSuffix(picture.getSuffix());
            pictureDAO.save(pictureIO);
            PictureTagIO pictureTag = new PictureTagIO();
            pictureTag.setPictureId(pictureIO.getId());
            pictureTag.setPictureUrl(pictureIO.getUrl());
            pictureSaveIO.getPictureTags().forEach(tag -> {
                pictureTag.setTagName(tag);
                pictureTagDAO.save(pictureTag);
            });
            //添加到redis
            addToRedis(pictureIO);
        });
        return true;
    }

    @Transactional
    @Override
    public boolean saveForSpider(PictureIO pictureIO) {
        //校验是否有存在的文件在db中
        String hex = QETag.data(pictureIO.getBytes());
        int count = pictureDAO.existsHash(hex);
        if(count > 0) {
            LOG.info("-----图片存在啦-----");
        } else {
            JSONObject jsonObject = null;
            try {
                jsonObject = qiniuUtil.upload(pictureIO.getBytes(), pictureIO.getSuffix());
            } catch (QiniuException e) {
                e.printStackTrace();
            }
            String url = jsonObject.getString("key");
            String hash = jsonObject.getString("hash");
            appendSaveParam(pictureIO, url, hash);
            boolean picFlag = pictureDAO.save(pictureIO);
            CommonExceptionHandler.flagCheck(picFlag);

            PictureTagIO pictureTag = new PictureTagIO();
            pictureTag.setPictureId(pictureIO.getId());
            pictureTag.setPictureUrl(pictureIO.getUrl());
            if (CollectionUtils.isNotEmpty(pictureIO.getTagList())) {
                pictureIO.getTagList().forEach(tagName -> {
                    pictureTag.setTagName(tagName);
                    boolean tagFlag = pictureTagDAO.save(pictureTag);
                    CommonExceptionHandler.flagCheck(tagFlag);
                });
            }
            //添加到redis
            addToRedis(pictureIO);
        }
        return true;
    }

    @Override
    public boolean obsolete(Long id) {
        boolean flag = pictureDAO.obsolete(id, StatusEnum.PICTURE_OBSOLETE.getCode());
        CommonExceptionHandler.flagCheck(flag);
        return flag;
    }

    /**
     * 将上传成功后的图片保存到redis
     * @param pictureIO
     */
    private void addToRedis(PictureIO pictureIO) {
        PictureListVO pictureListVO = new PictureListVO();
        pictureListVO.setId(pictureIO.getId());
        pictureListVO.setCatalogId(pictureIO.getCatalogId());
        pictureListVO.setUrl(pictureIO.getUrl());
        pictureListVO.setCount(0L);
        zsetCache.cacheZSet(RedisEnum.PICTURE.getKey() + pictureIO.getCatalogId(), pictureListVO, 0L);
    }

    /**
     * 组装save接口所需参数
     *
     * @param pictureIO：append到的io
     * @param pictureSaveIO：append来源
     */
    private final void appendSaveParam(PictureIO pictureIO, final PictureSaveIO pictureSaveIO) {
        pictureIO.setCatalogId(pictureSaveIO.getCatalogId());
        pictureIO.setCreatorId(pictureSaveIO.getCreatorId());
        pictureIO.setStatus(pictureSaveIO.getStatus());
    }

    /**
     * 组装save接口所需参数
     * 
     * @param pictureIO：append到的io
     * @param url：url
     * @param hash：hash值
     */
    private final void appendSaveParam(PictureIO pictureIO, final String url, final String hash) {
    	pictureIO.setUrl(url);
        pictureIO.setHash(hash);
        //如果为null，则代表是爬虫爬来的
        if(null == pictureIO.getCreatorId()) {
            pictureIO.setCreatorId(-1L);
        }
    }

    /**
     * 获取分页开始结束下标。redis分页用
     *
     * @param pageSize 每页条数
     * @param pageNum 第几页
     * @return
     */
    private final Map<String, Integer> getPage(Integer pageSize, Integer pageNum) {
        //默认（第一页）显示从0到9的10条数据
        int start = 0;
        int end = pageSize - 1;
        //如果点击了下一页（并非第一页）则运算
        if(pageNum > 1) {
			/*
			 * 因为从redis里读取，而redis下标从0开始，所以start是pageSize * (pageNum - 1)，
			 * 而不是pageSize * (pageNum - 1) + 1
			 * 相对应的end是end = (pageNum * pageSize) - 1;而不是end = pageNum * pageSize;
			 */
            start = pageSize * (pageNum - 1);
            end = (pageNum * pageSize) - 1;
        }
        Map<String, Integer> map = new HashMap<>();
        map.put("start", start);
        map.put("end", end);
        return map;
    }
}