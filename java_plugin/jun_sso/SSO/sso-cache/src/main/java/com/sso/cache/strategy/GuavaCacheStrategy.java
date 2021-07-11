package com.sso.cache.strategy;

import org.springframework.cache.Cache;
import org.springframework.stereotype.Component;

import com.sso.cache.CacheEvictTask;
import com.sso.cache.adapt.GuavaCache;
import com.sso.cache.adapt.GuavaCacheManager;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.Callable;

/**
 * Guava缓存策略
 */
@Component
public class GuavaCacheStrategy implements CacheStrategy {
    private GuavaCache cache;
    private Timer timer = new Timer(); //定时器（定时删除缓存）
    @Resource
    private GuavaCacheManager guavaCacheManager;

    @PostConstruct
    public void init() {
        cache = (GuavaCache) guavaCacheManager.getCache("guava-default");
    }

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @return 缓存对象
     */
    @Override
    public Object get(Object key) {
        Cache.ValueWrapper wrapper = cache.get(key);
        return wrapper == null ? null : wrapper.get();
    }

    /**
     * 获取缓存对象
     *
     * @param key  键
     * @param type 值的class对象
     * @return 缓存对象
     */
    @Override
    public <T> T get(Object key, Class<T> type) {
        return get(key) == null ? null : (T) get(key);
    }

    /**
     * 获取缓存对象，不存在时调用callable获取
     *
     * @param key         键
     * @param valueLoader 缓存中没有时，获取值的接口
     * @return 缓存对象
     */
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return cache.get(key, valueLoader);
    }

    /**
     * 获取缓存对象，不存在时调用callable获取，并设置有效时间
     *
     * @param key         键
     * @param timeout     有效时间（单位为秒）
     * @param valueLoader 缓存中没有时，获取值的接口
     * @return 缓存对象
     */
    @Override
    public <T> T get(Object key, long timeout, Callable<T> valueLoader) {
        T value = get(key, valueLoader);
        expire(key, timeout);
        return value;
    }

    /**
     * 获取缓存对象，不存在时调用callable获取，并设置过期时间
     *
     * @param key         键
     * @param expireTime  过期时间
     * @param valueLoader 缓存中没有时，获取值的接口
     * @return 缓存对象
     */
    @Override
    public <T> T get(Object key, Date expireTime, Callable<T> valueLoader) {
        T value = get(key, valueLoader);
        expireAt(key, expireTime);
        return value;
    }

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(Object key, Object value) {
        cache.put(key, value);
    }

    /**
     * 写入缓存，并指定有效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（单位为秒）
     */
    @Override
    public void put(Object key, Object value, long timeout) {
        put(key, value);
        expire(key, timeout);
    }

    /**
     * 写入缓存，并指定过期时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    @Override
    public void put(Object key, Object value, Date expireTime) {
        put(key, value);
        expireAt(key, expireTime);
    }

    /**
     * 仅当缓存中没有时写入
     *
     * @param key   键
     * @param value 值
     * @return 是否有写入
     */
    @Override
    public boolean putIfAbsent(Object key, Object value) {
        Cache.ValueWrapper wrapper = cache.putIfAbsent(key, value);
        return wrapper == null;
    }

    /**
     * 仅当缓存中没有时写入，并指定有效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间
     * @return 是否有写入
     */
    @Override
    public boolean putIfAbsent(Object key, Object value, long timeout) {
        boolean done = putIfAbsent(key, value);
        if (done) {
            expire(key, timeout);
        }
        return done;
    }

    /**
     * 仅当缓存中没有时写入，并指定过期时间
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     * @return 是否有写入
     */
    @Override
    public boolean putIfAbsent(Object key, Object value, Date expireTime) {
        boolean done = putIfAbsent(key, value);
        if (done) {
            expireAt(key, expireTime);
        }
        return done;
    }

    /**
     * 移除key对应的缓存
     *
     * @param key 键
     */
    @Override
    public void evict(Object key) {
        cache.evict(key);
    }

    /**
     * 清除所有缓存
     */
//    @Override
//    public void clear() {
//        cache.clear();
//    }

    /**
     * 设置缓存有效时间
     *
     * @param key     键
     * @param timeout 有效时间
     */
    @Override
    public void expire(Object key, long timeout) {
        timer.schedule(new CacheEvictTask(this, key), timeout * 1000);
    }

    /**
     * 设置缓存过期时间
     *
     * @param key        键
     * @param expireTime 过期时间
     */
    @Override
    public void expireAt(Object key, Date expireTime) {
        timer.schedule(new CacheEvictTask(this, key), expireTime);
    }
}
