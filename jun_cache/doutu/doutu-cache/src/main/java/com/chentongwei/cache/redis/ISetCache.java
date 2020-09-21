package com.chentongwei.cache.redis;

import java.util.Set;

/**
 * Set类型的操作
 *
 * @author TongWei.Chen 2017-05-19 10:07:00
 */
public interface ISetCache {

    /**
     * 缓存set类型
     *
     * @param key key
     * @param values 值
     * @return
     */
    long cacheSet(final String key, final String ... values);

    /**
     * 获取Set集合
     *
     * @param key key
     * @return
     */
    Set<String> getSet(final String key);
}
