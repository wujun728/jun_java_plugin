package com.chentongwei.service.common;

/**
 * 容器启动时执行的Service。负责将一些data缓存到redis
 *
 * @author TongWei.Chen 2017-05-19 15:31:33
 */
public interface IInitDataService {
    /**
     * 将图片分类名称放到redis的list中
     */
    void initCatalog();
    
    /**
     * 将图片缓存到redis的zset中（将图片分类id作为key）
     */
    void initPicture();

    /**
     * 将图片按照时间倒序排序放到redis的list
     */
    void initIndex();
}
