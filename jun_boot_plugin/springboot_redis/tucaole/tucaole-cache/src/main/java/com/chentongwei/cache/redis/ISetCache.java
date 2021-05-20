package com.chentongwei.cache.redis;

import java.util.Set;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Set类型的操作
 */
public interface ISetCache {

    /**
     * 根据key获取set集合长度
     *
     * @param key：key
     * @return
     */
    long count(final String key);

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

    /**
     * 移除Set集合中的某个元素
     *
     * @param key
     * @param values
     * @return
     */
    long removeSet(final String key, final String ... values);

    /**
     * 判断元素是否存在于集合中
     *
     * @param key：key
     * @param value：value
     * @return
     */
    boolean exists(final String key, final String value);
}
