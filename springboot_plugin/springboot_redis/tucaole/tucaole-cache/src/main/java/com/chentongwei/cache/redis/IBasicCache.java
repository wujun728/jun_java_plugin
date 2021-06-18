package com.chentongwei.cache.redis;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的String类型
 */
public interface IBasicCache {

    /**
     * 自增
     *
     * @param key key
     * @param count 自增值
     */
    long increment(final String key, final long count);

    /**
     * 查询所有匹配的key
     *
     * @param pattern：表达式
     * @return
     */
    Set<String> keys(final String pattern);

    /**
     * 设置一个字符串
     *
     * @param key key
     * @param value 值
     */
    void set(final String key, final String value);

    /**
     * 设置一个字符串（有效期的）
     *
     * @param key：key
     * @param value：值
     * @param time：过期时间
     * @param timeUnit：单位
     */
    void set(final String key, final String value, long time, TimeUnit timeUnit);

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

    /**
     * 删除key集合
     *
     * @param keys：key的集合
     */
    void deleteKey(final Collection<String> keys);
}
