package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.IListCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Redis的List操作
 *
 * @author TongWei.Chen 2017-05-19 09:23:34
 */
@Repository
public class ListCacheImpl implements IListCache {
    private static final Logger LOG = LoggerFactory.getLogger(ListCacheImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public boolean cacheList(final String key, final String value, final long time) {
        try {
            //序列化key，否则用basicCache里面的delKey无法将其删除
            RedisSerializer<String> serializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(serializer);
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            LOG.error("缓存list失败，key：" + key, "；value：" + value, e);
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean cacheList(final String key, final String value) {
        return cacheList(key, value, -1);
    }

    @Override
    public List<String> getList(final String key, final long start, final long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            LOG.error("获取list失败，key：" + key, e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<String> getList(final String key) {
        return getList(key, 0L, -1L);
    }

    @Override
    public long size(String key) {
        return redisTemplate.opsForList().size(key);
    }
}