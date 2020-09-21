package com.chentongwei.service.common.impl;

import java.util.List;

import com.chentongwei.cache.redis.IListCache;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chentongwei.cache.redis.IBasicCache;
import com.chentongwei.cache.redis.IZSetCache;
import com.chentongwei.common.constant.RedisEnum;
import com.chentongwei.dao.doutu.ICatalogDAO;
import com.chentongwei.dao.doutu.IPictureDAO;
import com.chentongwei.entity.doutu.vo.CatalogVO;
import com.chentongwei.entity.doutu.vo.PictureListVO;
import com.chentongwei.service.common.IInitDataService;

/**
 * 容器启动时执行的Service。负责将一些data缓存到redis
 *
 * @author TongWei.Chen 2017-05-19 15:32:19
 */
@Service
public class InitDataServiceImpl implements IInitDataService {
    private static final Logger LOG = LoggerFactory.getLogger(InitDataServiceImpl.class);

    @Autowired
    private ICatalogDAO catalogDAO;
    @Autowired
    private IBasicCache basicCache;
    @Autowired
    private IZSetCache zsetCache;
    @Autowired
    private IListCache listCache;
    @Autowired
    private IPictureDAO pictureDAO;
    
    @Override
    public void initCatalog() {
        List<CatalogVO> list = catalogDAO.list();
        basicCache.set(RedisEnum.CATALOG.getKey(), JSON.toJSONString(list));
        LOG.info("缓存分类信息完成");
    }

	@Override
	public void initPicture() {
		List<CatalogVO> catalogList = catalogDAO.list();
		for(CatalogVO catalog : catalogList) {
            List<PictureListVO> pictureList = pictureDAO.listByCatalogId(catalog.getId());
            if(CollectionUtils.isEmpty(pictureList)) {
                basicCache.deleteKey(RedisEnum.PICTURE.getKey() + catalog.getId());
                continue ;
            }
            /*本想着优化用，免得启动的时候耗费时间和性能，想想还是算了...没必要...*/
            /*long size = zsetCache.zCard(RedisEnum.PICTURE.getKey() + catalog.getId());
            if(pictureList.size() == size) {
                continue ;
            }*/
            basicCache.deleteKey(RedisEnum.PICTURE.getKey() + catalog.getId());
            pictureList.forEach(picture -> {
            	//这里score=0是因为防止分页取数据的时候按照分数降序排序。这样就能按照时间最新来排序了
                zsetCache.cacheZSet(RedisEnum.PICTURE.getKey() + catalog.getId(), picture, 0);
            });
        }
		LOG.info("缓存图片信息完成");
	}

    @Override
    public void initIndex() {
        basicCache.deleteKey(RedisEnum.PICTURE.getKey() + RedisEnum.PICTURE_INDEX.getKey());
        List<PictureListVO> list = pictureDAO.list();
        list.forEach(picture -> {
            listCache.cacheList(RedisEnum.PICTURE.getKey() + RedisEnum.PICTURE_INDEX.getKey(), JSON.toJSONString(picture));
        });
        LOG.info("缓存图片主页信息完成");
    }
}