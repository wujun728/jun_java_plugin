package com.tanghd.cache.loader;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.tanghd.cache.builder.CacheBuilder;
import com.tanghd.cache.serialize.Serializer;
import com.tanghd.cache.util.RedisUtil;

public class RedisCacheLoader extends AbstractCacheLoader {

    private static final String REFRESH_SUBFIX = ".refresh_subfix";
    private static final String EXPIRE_SUBFIX = ".expireAt";

    private RedisUtil redisClient;

    private int dbIndex = 0;

    private String getCacheKey(String key, CacheBuilder<?> builder) {
        return builder.getKeyPrefix() + key;
    }

    private byte[] getFromRedis(String key, String cacheKey, CacheBuilder<?> builder) {
        byte[] ckBytes = cacheKey.getBytes();
        try {
            byte[] t = redisClient.getAndReturn(dbIndex, ckBytes);
            return t;
        } catch (Exception e) {
            log.error("getByteFromRedis cacheKey=" + cacheKey + ",error:", e);
        }
        return null;
    }

    @Override
    public <T> void saveData(String cacheKey, CacheBuilder<T> builder, T result) throws Exception {
        if (null != result) {
            byte[] t = serializer.serialize(result);
            redisClient.setAndReturn(dbIndex, cacheKey.getBytes(), t, builder.getExpireSeconds());
        }
    }

    @Override
    public <T> T get(String key, Class<T> clz, CacheBuilder<T> builder) throws Exception {
        String cacheKey = getCacheKey(key, builder);
        T result = null;
        byte[] t = getFromRedis(key, cacheKey, builder);
        if (null == t) {
            result = loadData(key, cacheKey, builder);
        } else {
            this.asyncEventBus.post(new RefreshEventMessage(key, builder));
            try {
                result = serializer.deserialize(t, clz);
            } catch (Exception e) {
                log.error("deserialize for key:" + key + ", error:", e);
            }
        }
        return result;
    }

    @Override
    public <T> List<T> getList(String key, Class<T> clz, CacheBuilder<List<T>> builder) throws Exception {
        String cacheKey = getCacheKey(key, builder);
        List<T> result = null;
        byte[] t = getFromRedis(key, cacheKey, builder);
        if (null == t) {
            result = loadData(key, cacheKey, builder);
        } else {
            this.asyncEventBus.post(new RefreshEventMessage(key, builder));
            try {
                result = serializer.deserializeList(t, clz);
            } catch (Exception e) {
                log.error("deserialize for key:" + key + ", error:", e);
            }
        }
        return result;
    }

    @Override
    public <T> Set<T> getSet(String key, Class<T> clz, CacheBuilder<Set<T>> builder) throws Exception {
        String cacheKey = getCacheKey(key, builder);
        Set<T> result = null;
        byte[] t = getFromRedis(key, cacheKey, builder);
        if (null == t) {
            result = loadData(key, cacheKey, builder);
        } else {
            refresh(key, builder);
            try {
                result = serializer.deserializeSet(t, clz);
            } catch (Exception e) {
                log.error("deserializeSet for key:" + key + ", error:", e);
            }
        }
        return result;
    }

    @Override
    public <K, V> Map<K, V> getMap(String key, Class<K> keyClz, Class<V> valueClz, CacheBuilder<Map<K, V>> builder)
            throws Exception {
        String cacheKey = getCacheKey(key, builder);
        Map<K, V> result = null;
        byte[] t = getFromRedis(key, cacheKey, builder);
        if (null == t) {
            result = loadData(key, cacheKey, builder);
        } else {
            this.asyncEventBus.post(new RefreshEventMessage(key, builder));
            try {
                result = serializer.deserializeMap(t, keyClz, valueClz);
            } catch (Exception e) {
                log.error("deserializeSet for key:" + key + ", error:", e);
            }
        }
        return result;
    }

    @Override
    public boolean needRefresh(String cacheKey, int expire, boolean autoRefresh) {
        if (!autoRefresh) {
            return false;
        }
        if (0 >= expire) {
            return false;
        }
        long time = -1;
        Long t = redisClient.ttl(dbIndex, cacheKey);
        if (-1 == t) {
            // 不会超时
            return false;
        }
        if (-2 == t || -3 == t) {
            // key不存在
            return false;
        }
        if (null != t) {
            time = t;
        }
        if (time > 0 && time <= 5) {
            if (null != t && 1 == t) {
                if (expire > 10) {
                    t = redisClient.setnx(dbIndex, cacheKey + REFRESH_SUBFIX,
                            String.valueOf(System.currentTimeMillis()), expire - 10);
                } else {
                    t = redisClient.setnx(dbIndex, cacheKey + REFRESH_SUBFIX,
                            String.valueOf(System.currentTimeMillis()), expire - 2);
                }
                return t == 1;
            }
        }
        return false;
    }

    @Override
    public <T> void refresh(final String key, final CacheBuilder<T> builder) throws Exception {
        String cacheKey = getCacheKey(key, builder);
        if (!needRefresh(cacheKey, builder.getExpireSeconds(), builder.getAutoRefresh())) {
            return;
        }
        if (log.isDebugEnabled()) {
            log.debug("refresh of key:" + cacheKey);
        }
        loadData(key, cacheKey, builder);
    }

    @Override
    public void delete(String key, CacheBuilder<?> builder) throws Exception {
        String cacheKey = getCacheKey(key, builder);
        redisClient.delete(dbIndex, cacheKey);
    }

    public RedisUtil getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisUtil redisClient) {
        this.redisClient = redisClient;
    }

    public int getDbIndex() {
        return dbIndex;
    }

    public void setDbIndex(int dbIndex) {
        this.dbIndex = dbIndex;
    }

}
