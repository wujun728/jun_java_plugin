package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.IBasicCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的String类型
 */
@Repository
public class BasicCacheImpl implements IBasicCache {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public long increment(final String key, final long count) {
        try {
            return stringRedisTemplate.opsForValue().increment(key, count);
        } catch (Exception e) {
            LOG.error("redis自增失败，key：【{}】; count：【{}】;", key, count);
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public Set<String> keys(final String pattern) {
        try {
            return stringRedisTemplate.keys(pattern);
        } catch (Exception e) {
            LOG.error("redis查询匹配【{}】的key失败：", pattern);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void set(final String key, final String value) {
        try {
            stringRedisTemplate.opsForValue().set(key, value);
        } catch (Exception e) {
            LOG.error("缓存字符串失败，key：【{}】; value：【{}】;", key, value);
            e.printStackTrace();
        }
    }

    @Override
    public void set(final String key, final String value, final long time, final TimeUnit timeUnit) {
        try {
            stringRedisTemplate.opsForValue().set(key, value, time, timeUnit);
        } catch (Exception e) {
            LOG.error("缓存字符串失败，key：【{}】; value：【{}】;", key, value);
            e.printStackTrace();
        }
    }

    @Override
    public String get(final String key) {
        try {
            return stringRedisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            LOG.error("缓存字符串失败，key：【{}】", key);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void deleteKey(final String key) {
        try {
            stringRedisTemplate.delete(key);
        } catch (Exception e) {
            LOG.error("删除key失败，key：【{}】", key);
            e.printStackTrace();
        }
    }

    @Override
    public void deleteKey(final Collection<String> keys) {
        try {
            stringRedisTemplate.delete(keys);
        } catch (Exception e) {
            LOG.error("删除keys【{}】失败:", keys);
        }
    }

}
