package com.chentongwei.cache.redis.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import com.chentongwei.cache.redis.IBasicCache;

/**
 * Redis的String类型
 *
 * @author TongWei.Chen 2017-05-18 16:39:56
 */
@Repository
public class BasicCacheImpl implements IBasicCache {
    private static final Logger LOG = LoggerFactory.getLogger(BasicCacheImpl.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void set(final String key, final String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            LOG.error("缓存字符串失败，key：" + key + "; value：" + value, e);
            e.printStackTrace();
        }
    }

    @Override
    public String get(final String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOG.error("缓存字符串失败，key：" + key, e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteKey(final String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            LOG.error("删除key失败，key为" + key, e);
            e.printStackTrace();
        }
    }
}
