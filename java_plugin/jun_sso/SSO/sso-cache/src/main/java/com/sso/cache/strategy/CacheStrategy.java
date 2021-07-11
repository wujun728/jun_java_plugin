package com.sso.cache.strategy;

import java.util.Date;
import java.util.concurrent.Callable;

/**
 * 缓存策略接口
 */
public interface CacheStrategy {

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @return 缓存对象
     */
    Object get(Object key);

    /**
     * 获取缓存对象
     *
     * @param key  键
     * @param type 值的class对象
     * @param <T>  值的类型
     * @return 缓存对象
     */
    <T> T get(Object key, Class<T> type);

    /**
     * 获取缓存对象，不存在时调用callable获取
     *
     * @param key         键
     * @param valueLoader 缓存中没有时，获取值的接口
     * @param <T>         值的类型
     * @return 缓存对象
     */
    <T> T get(final Object key, final Callable<T> valueLoader);

    /**
     * 获取缓存对象，不存在时调用callable获取，并设置有效时间
     *
     * @param key         键
     * @param timeout     有效时间（单位为秒）
     * @param valueLoader 缓存中没有时，获取值的接口
     * @param <T>         值的类型
     * @return 缓存对象
     */
    <T> T get(final Object key, long timeout, final Callable<T> valueLoader);

    /**
     * 获取缓存对象，不存在时调用callable获取，并设置过期时间
     *
     * @param key         键
     * @param expireTime  过期时间
     * @param valueLoader 缓存中没有时，获取值的接口
     * @param <T>         值的类型
     * @return 缓存对象
     */
    <T> T get(final Object key, Date expireTime, final Callable<T> valueLoader);

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     */
    void put(Object key, Object value);

    /**
     * 写入缓存，并指定有效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（单位为秒）
     */
    void put(Object key, Object value, long timeout);

    /**
     * 写入缓存，并指定过期时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    void put(Object key, Object value, Date expireTime);

    /**
     * 仅当缓存中没有时写入
     *
     * @param key   键
     * @param value 值
     * @return 是否有写入
     */
    boolean putIfAbsent(Object key, Object value);

    /**
     * 仅当缓存中没有时写入，并指定有效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间
     * @return 是否有写入
     */
    boolean putIfAbsent(Object key, Object value, long timeout);

    /**
     * 仅当缓存中没有时写入，并指定过期时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @return 是否有写入
     */
    boolean putIfAbsent(Object key, Object value, Date expireTime);

    /**
     * 移除key对应的缓存
     *
     * @param key 键
     */
    void evict(Object key);

    /**
     * 清除所有缓存
     */
//    void clear();

    /**
     * 设置缓存有效时间
     *
     * @param key     键
     * @param timeout 有效时间
     */
    void expire(Object key, long timeout);

    /**
     * 设置缓存过期时间
     *
     * @param key        键
     * @param expireTime 过期时间
     */
    void expireAt(Object key, Date expireTime);
}
