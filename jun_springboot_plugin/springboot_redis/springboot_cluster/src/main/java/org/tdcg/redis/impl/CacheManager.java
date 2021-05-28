package org.tdcg.redis.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.tdcg.redis.ICacheManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Title: CacheManager
 * @Package: org.tdcg.redis.impl
 * @Description:
 * @Author: 二东 <zwd_1222@126.com>
 * @date: 2016/10/24
 * @Version: V1.0
 */
@Service("cacheManager")
public final class CacheManager {

    private static final Logger logger = Logger.getLogger(CacheManager.class);
    // 超时时间，单位为秒
    private final int expiration = 3600;
    @Resource
    private ICacheManager iCacheManager;

    public Object getCache(String cacheKey) {
        return iCacheManager.getCache(cacheKey);
    }

    public boolean putCache(String cacheKey, Object objValue) {
        return putCache(cacheKey, objValue, expiration);
    }

    public boolean putCache(String cacheKey, Object objValue, int expiration) {
        try {
            if (expiration <= 0) {
                expiration = this.expiration;
            }
            return iCacheManager.putCache(cacheKey.toString(), objValue, expiration);
        } catch (Throwable ex) {
            logger.error("Cannot set the cache with the cacheKey:" + cacheKey.toString(), ex);
        }
        return false;
    }

    public boolean removeCache(String cacheKey) {
        try {
            iCacheManager.removeCache(cacheKey);
            return true;
        } catch (Throwable ex) {
            logger.error("Cannot remove the cache with the cacheKey:" + cacheKey.toString(), ex);
            return false;
        }
    }

    /**
     * 将对象以list集合形式存放
     *
     * @param cacheKey
     * @param objValue
     * @param objValue
     * @return
     */
    public boolean putListCache(String cacheKey, Object objValue) {
        try {
            return iCacheManager.putListCache(cacheKey, objValue);
        } catch (Throwable ex) {
            logger.error("Cannot put the list cache with the cacheKey:" + cacheKey.toString(), ex);
            return false;
        }
    }

    /**
     * 将对象存放在list集合指定index位置
     *
     * @param cacheKey
     * @param objValue
     * @param index
     * @return
     */
    public boolean putListCache(String cacheKey, Object objValue, int index) {
        try {
            return iCacheManager.putListCache(cacheKey, objValue, index);
        } catch (Throwable ex) {
            logger.error("Cannot put the list cache in the index with the cacheKey:" + cacheKey.toString(), ex);
            return false;
        }
    }

    /**
     * 获取list集合全部结果集
     *
     * @param cacheKey
     * @return
     */
    public List<Object> getListCache(String cacheKey) {
        try {
            return iCacheManager.getListCache(cacheKey);
        } catch (Throwable ex) {
            logger.error("Cannot get the list cache with the cacheKey:" + cacheKey.toString(), ex);
            return null;
        }
    }

    /**
     * 获取指定索引段内的集合
     *
     * @param cacheKey
     * @return
     */
    public List<Object> getListCache(String cacheKey, int start, int end) {
        try {
            return iCacheManager.getListCache(cacheKey, start, end);
        } catch (Throwable ex) {
            logger.error("Cannot get the list cache between start and end with the cacheKey:" + cacheKey.toString(), ex);
            return null;
        }
    }

    /**
     * 删减list集合指定坐标范围内数据
     *
     * @param cacheKey
     * @param start    起始坐标
     * @param end      结束坐标
     * @return
     */
    public boolean trimListCache(String cacheKey, int start, int end) {
        try {
            return iCacheManager.trimListCache(cacheKey, start, end);
        } catch (Throwable ex) {
            logger.error("Cannot get the list cache with the cacheKey:" + cacheKey.toString(), ex);
            return false;
        }
    }

    /**
     * 存放map集合缓存
     *
     * @param cacheKey
     * @param map
     * @return
     */
    public boolean putMapCache(String cacheKey, Map map) {
        try {
            return iCacheManager.putMapCache(cacheKey, map);
        } catch (Throwable ex) {
            logger.error("Cannot put the map cache with the cacheKey:" + cacheKey.toString(), ex);
            return false;
        }
    }

    /**
     * 删除Map集合中指定key-value
     *
     * @param cacheKey
     * @param mapKey
     * @return
     */
    public boolean deleteMapCache(String cacheKey, String mapKey) {
        try {
            return iCacheManager.deleteMapCache(cacheKey, mapKey);
        } catch (Throwable ex) {
            logger.error("Cannot delete the map cache with the cacheKey:" + cacheKey.toString() + " and the mapKey:" + mapKey, ex);
            return false;
        }
    }

    /**
     * 根据key查询集合中的value值
     *
     * @param cacheKey
     * @param mapKey
     * @return
     */
    public Object getMapValueCache(String cacheKey, String mapKey) {
        try {
            return iCacheManager.deleteMapCache(cacheKey, mapKey);
        } catch (Throwable ex) {
            logger.error("Cannot get the map value cache with the cacheKey:" + cacheKey.toString() + " and the mapKey:" + mapKey, ex);
            return false;
        }
    }
}

