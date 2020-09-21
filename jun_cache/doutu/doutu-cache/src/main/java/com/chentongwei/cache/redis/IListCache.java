package com.chentongwei.cache.redis;

import java.util.List;

/**
 * Redis的List操作
 *
 * @author TongWei.Chen 2017-05-19 09:23:06
 */
public interface IListCache {

    /**
     * 添加list
     *
     * @param key key
     * @param value 值
     * @param time 过期时间
     * @return
     */
    boolean cacheList(final String key, final String value, final long time);

    /**
     * 添加list
     *
     * @param key key
     * @param value 值
     * @return
     */
    boolean cacheList(final String key, final String value);

    /**
     * 获取List
     *
     * @param key key
     * @param start 开始下标
     * @param end 结束下标
     * @return
     */
    List<String> getList(final String key, final long start, final long end);

    /**
     * 获取全部List
     *
     * @param key key
     * @return
     */
    List<String> getList(final String key);

    /**
     * 获取列表长度
     *
     * @param key：key
     * @return
     */
    long size(final String key);
}
