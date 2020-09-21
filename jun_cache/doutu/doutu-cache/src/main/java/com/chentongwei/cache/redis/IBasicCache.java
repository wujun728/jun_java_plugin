package com.chentongwei.cache.redis;

/**
 * Redis的String类型
 *
 * @author TongWei.Chen 2017-05-18 16:40:13
 */
public interface IBasicCache {

    /**
     * 设置一个字符串
     *
     * @param key key
     * @param value 值
     */
    void set(final String key, final String value);

    /**
     * 根据key获取value
     *
     * @param key key
     * @return
     */
    String get(final String key);

    /**
     * 删除key
     *
     * @param key key
     */
    void deleteKey(final String key);
}
