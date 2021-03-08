package com.gosalelab.provider.impl;

import com.alibaba.fastjson.JSON;
import com.gosalelab.provider.AbstractCacheProvider;
import com.gosalelab.provider.CacheProvider;
import com.gosalelab.constants.CacheConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Type;

/**
 * @author Wujun
 */
@Component("redisCacheProvider")
@ConditionalOnProperty(prefix = CacheConstants.ASPECT_CACHE_PREFIX, name = "provider", havingValue = CacheConstants.CACHE_REDIS_NAME)
public class RedisCacheProvider extends AbstractCacheProvider implements CacheProvider {
    private static Logger logger = LoggerFactory.getLogger(RedisCacheProvider.class);

    private final JedisPool jedisPool;

    @Autowired
    public RedisCacheProvider(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    @Override
    public void put(String key, Object value, Integer expire) {
        try {
            String valueStr = JSON.toJSONString(value);
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.setex(key, expire, valueStr);
            }
        } catch (Exception e) {
            logger.error("cache put fail, key: " + key + ",value: " + value + ",expire: " + expire, e);
        }
    }

    @Override
    public Object get(String key, Type returnType) {
        try {
            String cacheValue;
            try (Jedis jedis = jedisPool.getResource()) {
                cacheValue = jedis.get(key);
            }
            return handleReturnValue(cacheValue, returnType);
        } catch (Exception e) {
            logger.error("cache get fail,key: " + key, e);
            return null;
        }
    }

    @Override
    public Boolean del(String key) {
        try {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.del(key);
            }
        } catch (Exception e) {
            logger.error("cache del fail, key: " + key, e);
            return false;
        }
        return true;
    }
}
