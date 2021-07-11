package com.sso.data.redis.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * redis缓存操作接口
 */
public interface RedisCacheOptions {

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     */
    void put(String key, Object value);

    /**
     * 写入缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（单位：秒）
     */
    void put(String key, Object value, long timeout);

    /**
     * 写入缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    void put(String key, Object value, Date expireTime);

    /**
     * 写入缓存（仅当不存在时写入）
     *
     * @param key   键
     * @param value 值
     */
    void putIfAbsent(String key, Object value);

    /**
     * 写入缓存（仅当不存在时写入）
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（单位：秒）
     */
    void putIfAbsent(String key, Object value, long timeout);

    /**
     * 写入缓存（仅当不存在时写入）
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    void putIfAbsent(String key, Object value, Date expireTime);

    /**
     * 批量写入缓存
     *
     * @param keyValues 键值对
     */
    void batchPut(Map<String, Object> keyValues);

    /**
     * 批量写入缓存
     *
     * @param keyValues 键值对
     * @param timeout   有效时间（单位：秒）
     */
    void batchPut(Map<String, Object> keyValues, long timeout);

    /**
     * 批量写入缓存
     *
     * @param keyValues  键值对
     * @param expireTime 过期时间
     */
    void batchPut(Map<String, Object> keyValues, Date expireTime);

    /**
     * 批量写入缓存（仅当不存在时写入）
     *
     * @param keyValues 键值对
     */
    void batchPutIfAbsent(Map<String, Object> keyValues);

    /**
     * 批量写入缓存（仅当不存在时写入）
     *
     * @param keyValues 键值对
     * @param timeout   有效时间（单位：秒）
     */
    void batchPutIfAbsent(Map<String, Object> keyValues, long timeout);

    /**
     * 批量写入缓存（仅当不存在时写入）
     *
     * @param keyValues  键值对
     * @param expireTime 过期时间
     */
    void batchPutIfAbsent(Map<String, Object> keyValues, Date expireTime);

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 值
     */
    Object get(String key);

    /**
     * 读取缓存
     *
     * @param key 键
     * @param cls class对象
     * @param <T> class类型
     * @return 值
     */
    <T> T get(String key, Class<T> cls);

    /**
     * 读取缓存（不存在时调用接口获取，并存入缓存）
     *
     * @param key      键
     * @param callable 缓存中不存在时，获取值的接口
     * @param <T>      class类型
     * @return 值
     */
    <T> T get(String key, Callable<T> callable);

    /**
     * 批量读取缓存
     *
     * @param keys 键集合
     * @return 值集合
     */
    List<Object> batchGet(Collection<String> keys);

    /**
     * 批量读取缓存
     *
     * @param keys 键集合
     * @param cls  class对象
     * @param <T>  class类型
     * @return 值集合
     */
    <T> List<T> batchGet(Collection<String> keys, Class<T> cls);

    /**
     * 清除缓存
     *
     * @param key 键
     */
    void delete(String key);

    /**
     * 批量清除缓存
     *
     * @param keys 键集合
     */
    void batchDelete(Collection<String> keys);

    /**
     * 设置缓存有效时间
     *
     * @param key     键
     * @param timeout 有效时间（单位：秒）
     */
    void expire(String key, long timeout);

    /**
     * 设置缓存过期时间
     *
     * @param key        键
     * @param expireTime 过期时间
     */
    void expireAt(String key, Date expireTime);

    /**
     * 批量设置缓存有效时间
     *
     * @param keys    键集合
     * @param timeout 有效时间（单位：秒）
     */
    void batchExpire(Collection<String> keys, long timeout);

    /**
     * 批量设置缓存过期时间
     *
     * @param keys       键集合
     * @param expireTime 过期时间
     */
    void batchExpireAt(Collection<String> keys, Date expireTime);

    /**
     * 是否存在缓存
     *
     * @param key 键
     * @return 存在->true;不存在->false
     */
    boolean exist(String key);

    /**
     * 清除所有缓存
     */
    void clear();

    void hPut(String key, String hashKey, Object value);

    Object hGet(String key, String hashKey);

    boolean hExist(String key, String hashKey);

    void hDel(String key, String hashKey);

    void hMultiDel(String key, List<String> hashKeys);
}
