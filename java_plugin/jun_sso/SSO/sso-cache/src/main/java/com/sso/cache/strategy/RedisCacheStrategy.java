package com.sso.cache.strategy;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheElement;
import org.springframework.data.redis.cache.RedisCacheKey;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存策略
 */
@Component
public class RedisCacheStrategy implements CacheStrategy {
    private RedisCache cache;
    private RedisTemplate template;
    @Resource
    private RedisCacheManager redisCacheManager;

    @PostConstruct
    public void init() {
        cache = (RedisCache) redisCacheManager.getCache("redis-default");
        template = (RedisTemplate) cache.getNativeCache();
    }

    /**
     * 获取缓存对象
     *
     * @param key 键
     * @return 缓存对象
     */
    @Override
    public Object get(Object key) {
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        Cache.ValueWrapper wrapper = cache.get(cacheKey);
        return wrapper == null ? null : wrapper.get();
    }

    /**
     * 获取缓存对象
     *
     * @param key  键
     * @param type 值的class对象
     * @param <T>  值的类型
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
     * @param <T>         值的类型
     * @return 缓存对象
     */
    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
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
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = new RedisCacheElement(cacheKey, value);
        cache.put(cacheElement);
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
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = new RedisCacheElement(cacheKey, value);
        cacheElement.setTimeToLive(timeout);
        cache.put(cacheElement);
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
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = new RedisCacheElement(cacheKey, value);
        cache.put(cacheElement);
        expireAt(cacheKey.getKeyElement(), expireTime);
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
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = new RedisCacheElement(cacheKey, value);
        Cache.ValueWrapper wrapper = cache.putIfAbsent(cacheElement);
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
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = cache.get(cacheKey);
        if (cacheElement == null) {
            cacheElement = new RedisCacheElement(cacheKey, value);
            cacheElement.setTimeToLive(timeout);
            cache.put(cacheElement);
            return true;
        }
        return false;
        /*RedisCacheElement cacheElement = new RedisCacheElement(cacheKey, value);
        cacheElement.setTimeToLive(timeout);
        //使用putIfAbsent方法，过期时间设置无效
        Cache.ValueWrapper wrapper = cache.putIfAbsent(cacheElement);
        return wrapper == null;*/
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
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = cache.get(cacheKey);
        if (cacheElement == null) {
            cacheElement = new RedisCacheElement(cacheKey, value);
            cache.put(cacheElement);
            expireAt(cacheKey.getKeyElement(), expireTime);
            return true;
        }
        return false;
    }

    /**
     * 移除key对应的缓存
     *
     * @param key 键
     */
    @Override
    public void evict(Object key) {
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        RedisCacheElement cacheElement = cache.get(cacheKey);
        if (cacheElement != null) {
            cache.evict(cacheElement);
        }
    }

    /**
     * 清除所有缓存
     */
//    @Override
//    public void clear() {
//        //cache.clear();
//        //当usePrefix为true且使用RedisCacheKey时，使用clear方法并不能清除缓存
//        RedisTemplate template = (RedisTemplate) cache.getNativeCache();
//        template.execute(new RedisCallback() {
//            @Override
//            public Object doInRedis(RedisConnection connection) throws DataAccessException {
//                connection.flushDb();
//                return null;
//            }
//        });
//    }

    /**
     * 设置缓存有效时间
     *
     * @param key     键
     * @param timeout 有效时间
     */
    @Override
    public void expire(Object key, long timeout) {
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        template.expire(cacheKey.getKeyElement(), timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存过期时间
     *
     * @param key        键
     * @param expireTime 过期时间
     */
    @Override
    public void expireAt(Object key, Date expireTime) {
        RedisCacheKey cacheKey = new RedisCacheKey(key);
        cacheKey.setSerializer(template.getKeySerializer());
        template.expireAt(cacheKey.getKeyElement(), expireTime);

    }
    
    /**
     * 将 键值对（hashKey-value）数据项设置到哈希表 key 中。
     * @param key
     * @param hashKey
     * @param value
     */
	public void hPut(String key, String hashKey, Object value) {
		byte[] rawKey = key.getBytes();
		byte[] rawHashKey = hashKey.getBytes();
		template.opsForHash().put(rawKey, rawHashKey, value);
	}

	/**
	 * 从哈希表 key 中获取hashKey对应项的值。
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public Object hGet(String key, String hashKey) {
		byte[] rawKey = key.getBytes();
		byte[] rawHashKey = hashKey.getBytes();
		return template.opsForHash().get(rawKey, rawHashKey);
	}

	/**
	 * 判断哈希表 key 中获取是否含有hashKey对应的项
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public boolean hHasKey(String key, String hashKey) {
		byte[] rawKey = key.getBytes();
		byte[] rawHashKey = hashKey.getBytes();
		return template.opsForHash().hasKey(rawKey, rawHashKey);
	}

	/**
	 * 哈希表 key 中删除含有hashKey对应的项
	 * @param key
	 * @param hashKey
	 * @return
	 */
	public long hDel(String key, String hashKey) {
		byte[] rawKey = key.getBytes();
		byte[] rawHashKey = hashKey.getBytes();
		return template.opsForHash().delete(rawKey, rawHashKey);
	}
	
	/**
	 * 批量删除
	 * @param key
	 * @param hashKeyArr
	 * @return
	 */
	public long hMultiDel(String key,List<String> hashKeyList){
		if(hashKeyList.isEmpty()){
			return -1L;
		}
		byte[] rawKey = key.getBytes();
		List<byte[]> byteList = new ArrayList<byte[]>();
		for(String hashKey : hashKeyList){
			byteList.add(hashKey.getBytes());
		}
		return template.opsForHash().delete(key, byteList.toArray());
	}
}
