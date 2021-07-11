package com.sso.data.redis.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.types.Expiration;
import org.springframework.data.redis.serializer.RedisSerializer;

import com.sso.data.redis.service.RedisCacheOptions;

/**
 * redis缓存操作实现类
 */
@SuppressWarnings("unchecked")
public class RedisCacheTemplate implements RedisCacheOptions {
    private static final String DEFAULT_KEY_SPACE = "default_cache";
    private RedisCache redisCache;
    private RedisTemplate redisTemplate;
    @Autowired
    private RedisCacheManager redisCacheManager;

    @PostConstruct
    public void init() {
        redisCache = (RedisCache) redisCacheManager.getCache(DEFAULT_KEY_SPACE);
        redisTemplate = (RedisTemplate) redisCache.getNativeCache();
    }

    /**
     * 写入缓存
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void put(final String key, final Object value) {
        redisCache.put(key, value);
    }

    /**
     * 写入缓存
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（单位：秒）
     */
    @Override
    public void put(final String key, final Object value, final long timeout) {
        this.put(key, value);
        redisTemplate.expire(addPrefix(key), timeout, TimeUnit.SECONDS);
    }

    /**
     * 写入缓存
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    @Override
    public void put(final String key, final Object value, final Date expireTime) {
        this.put(key, value);
        redisTemplate.expireAt(addPrefix(key), expireTime);
    }

    /**
     * 写入缓存（仅当不存在时写入）
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void putIfAbsent(final String key, final Object value) {
        redisCache.putIfAbsent(key, value);
    }

    /**
     * 写入缓存（仅当不存在时写入）
     *
     * @param key     键
     * @param value   值
     * @param timeout 有效时间（单位：秒）
     */
    @Override
    public void putIfAbsent(final String key, final Object value, final long timeout) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                connection.set(keySerializer.serialize(addPrefix(key)), valueSerializer.serialize(value),
                        Expiration.seconds(timeout), RedisStringCommands.SetOption.SET_IF_ABSENT);
                return null;
            }
        });
    }

    /**
     * 写入缓存（仅当不存在时写入）
     *
     * @param key        键
     * @param value      值
     * @param expireTime 过期时间
     */
    @Override
    public void putIfAbsent(final String key, final Object value, final Date expireTime) {
        redisTemplate.execute(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException { RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                connection.set(keySerializer.serialize(addPrefix(key)), valueSerializer.serialize(value),
                        Expiration.milliseconds(expireTime.getTime() - new Date().getTime()),
                        RedisStringCommands.SetOption.SET_IF_ABSENT);
                return null;
            }
        });
    }

    /**
     * 批量写入缓存
     *
     * @param keyValues 键值对
     */
    @Override
    public void batchPut(final Map<String, Object> keyValues) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                for (Map.Entry<String, Object> entry: keyValues.entrySet()) {
                    connection.set(keySerializer.serialize(addPrefix(entry.getKey())), valueSerializer.serialize(entry.getValue()));
                }
                return null;
            }
        });
    }

    /**
     * 批量写入缓存
     *
     * @param keyValues 键值对
     * @param timeout   有效时间（单位：秒）
     */
    @Override
    public void batchPut(final Map<String, Object> keyValues, final long timeout) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                for (Map.Entry<String, Object> entry: keyValues.entrySet()) {
                    connection.set(keySerializer.serialize(addPrefix(entry.getKey())), valueSerializer.serialize(entry.getValue()),
                            Expiration.seconds(timeout), RedisStringCommands.SetOption.UPSERT);
                }
                return null;
            }
        });
    }

    /**
     * 批量写入缓存
     *
     * @param keyValues  键值对
     * @param expireTime 过期时间
     */
    @Override
    public void batchPut(final Map<String, Object> keyValues, final Date expireTime) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                for (Map.Entry<String, Object> entry: keyValues.entrySet()) {
                    connection.set(keySerializer.serialize(addPrefix(entry.getKey())), valueSerializer.serialize(entry.getValue()),
                            Expiration.milliseconds(expireTime.getTime() - new Date().getTime()),
                            RedisStringCommands.SetOption.UPSERT);
                }
                return null;
            }
        });
    }

    /**
     * 批量写入缓存（仅当不存在时写入）
     *
     * @param keyValues 键值对
     */
    @Override
    public void batchPutIfAbsent(final Map<String, Object> keyValues) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                for (Map.Entry<String, Object> entry: keyValues.entrySet()) {
                    connection.set(keySerializer.serialize(addPrefix(entry.getKey())), valueSerializer.serialize(entry.getValue()),
                            Expiration.persistent(), RedisStringCommands.SetOption.SET_IF_ABSENT);
                }
                return null;
            }
        });
    }

    /**
     * 批量写入缓存（仅当不存在时写入）
     *
     * @param keyValues 键值对
     * @param timeout   有效时间（单位：秒）
     */
    @Override
    public void batchPutIfAbsent(final Map<String, Object> keyValues, final long timeout) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                for (Map.Entry<String, Object> entry: keyValues.entrySet()) {
                    connection.set(keySerializer.serialize(addPrefix(entry.getKey())), valueSerializer.serialize(entry.getValue()),
                            Expiration.seconds(timeout), RedisStringCommands.SetOption.SET_IF_ABSENT);
                }
                return null;
            }
        });
    }

    /**
     * 批量写入缓存（仅当不存在时写入）
     *
     * @param keyValues  键值对
     * @param expireTime 过期时间
     */
    @Override
    public void batchPutIfAbsent(final Map<String, Object> keyValues, final Date expireTime) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                for (Map.Entry<String, Object> entry: keyValues.entrySet()) {
                    connection.set(keySerializer.serialize(addPrefix(entry.getKey())), valueSerializer.serialize(entry.getValue()),
                            Expiration.milliseconds((expireTime.getTime() - new Date().getTime())),
                            RedisStringCommands.SetOption.SET_IF_ABSENT);
                }
                return null;
            }
        });
    }

    /**
     * 读取缓存
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        Cache.ValueWrapper wrapper = redisCache.get(key);
        return wrapper == null ? null : wrapper.get();
    }

    /**
     * @param key 键
     * @param cls class对象
     * @return 值
     */
    @Override
    public <T> T get(String key, Class<T> cls) {
        return this.get(key) == null ? null : (T) this.get(key);
    }

    /**
     * 读取缓存（不存在时调用接口获取，并存入缓存）
     *
     * @param key      键
     * @param callable 缓存中不存在时，获取值的接口
     * @return 值
     */
    @Override
    public <T> T get(String key, Callable<T> callable) {
        return redisCache.get(key, callable);
    }

    /**
     * 批量读取缓存
     *
     * @param keys 键集合
     * @return 值集合
     */
    @Override
    public List<Object> batchGet(Collection<String> keys) {
        Collection<String> newKeys = new ArrayList<>();
        for (String key: keys) {
            newKeys.add(this.addPrefix(key));
        }
        return redisTemplate.opsForValue().multiGet(newKeys);
    }

    /**
     * 批量读取缓存
     *
     * @param keys 键集合
     * @param cls  class对象
     * @return 值集合
     */
    @Override
    public <T> List<T> batchGet(Collection<String> keys, Class<T> cls) {
        Collection<String> newKeys = new ArrayList<>();
        for (String key: keys) {
            newKeys.add(this.addPrefix(key));
        }
        return redisTemplate.opsForValue().multiGet(newKeys);
    }

    /**
     * 清除缓存
     *
     * @param key 键
     */
    @Override
    public void delete(String key) {
        redisCache.evict(key);
    }

    /**
     * 批量清除缓存
     *
     * @param keys 键集合
     */
    @Override
    public void batchDelete(Collection<String> keys) {
        Collection<String> newKeys = new ArrayList<>();
        for (String key: keys) {
            newKeys.add(this.addPrefix(key));
        }
        redisTemplate.delete(newKeys);
    }

    /**
     * 设置缓存有效时间
     *
     * @param key     键
     * @param timeout 有效时间（单位：秒）
     */
    @Override
    public void expire(String key, final long timeout) {
        redisTemplate.expire(addPrefix(key), timeout, TimeUnit.SECONDS);
    }

    /**
     * 设置缓存过期时间
     *
     * @param key        键
     * @param expireTime 过期时间
     */
    @Override
    public void expireAt(String key, final Date expireTime) {
        redisTemplate.expireAt(addPrefix(key), expireTime);
    }

    /**
     * 批量设置缓存有效时间
     *
     * @param keys    键集合
     * @param timeout 有效时间（单位：秒）
     */
    @Override
    public void batchExpire(final Collection<String> keys, final long timeout) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                for (String key: keys) {
                    connection.expire(keySerializer.serialize(addPrefix(key)), timeout);
                }
                return null;
            }
        });
    }

    /**
     * 批量设置缓存过期时间
     *
     * @param keys       键集合
     * @param expireTime 过期时间
     */
    @Override
    public void batchExpireAt(final Collection<String> keys, final Date expireTime) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                for (String key: keys) {
                    connection.expireAt(keySerializer.serialize(addPrefix(key)), expireTime.getTime() / 1000);
                }
                return null;
            }
        });
    }

    /**
     * 是否存在缓存
     *
     * @param key 键
     * @return 存在->true;不存在->false
     */
    @Override
    public boolean exist(String key) {
        return redisTemplate.hasKey(addPrefix(key));
    }

    /**
     * 清除所有缓存
     */
    @Override
    public void clear() {
        redisCache.clear();
    }

    @Override
    public void hPut(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(addPrefix(key), hashKey, value);
    }

    @Override
    public Object hGet(String key, String hashKey) {
        return redisTemplate.opsForHash().get(addPrefix(key), hashKey);
    }

    @Override
    public boolean hExist(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(addPrefix(key), hashKey);
    }

    @Override
    public void hDel(String key, String hashKey) {
        redisTemplate.opsForHash().delete(addPrefix(key), hashKey);
    }

    @Override
    public void hMultiDel(String key, List<String> hashKeys) {
        redisTemplate.opsForHash().delete(addPrefix(key), hashKeys);
    }

    /**
     * 给键添加前缀
     *
     * @param key 键
     * @return 添加前缀后的键
     */
    private String addPrefix(String key) {
        return redisCache.getName() + ":" + key;
    }
}
