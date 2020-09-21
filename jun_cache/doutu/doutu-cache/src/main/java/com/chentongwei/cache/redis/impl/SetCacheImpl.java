package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.ISetCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Redis的set类型的操作
 *
 * @author TongWei.Chen 2017-05-19 10:07:10
 */
@Repository
public class SetCacheImpl implements ISetCache {
    private static final Logger LOG = LoggerFactory.getLogger(SetCacheImpl.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public long cacheSet(final String key, final String... values) {
        try {
            //序列化key，否则用basicCache里面的delKey无法将其删除
            RedisSerializer<String> serializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(serializer);
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            LOG.error("Redis的cacheSet失败: key为" + key + ": value为" + values, e);
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public Set<String> getSet(final String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            LOG.error("Redis的getSet失败: key为" + key, e);
            e.printStackTrace();
        }
        return null;
    }
}
