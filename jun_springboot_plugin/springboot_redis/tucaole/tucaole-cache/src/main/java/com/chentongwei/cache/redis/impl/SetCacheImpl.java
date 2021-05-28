package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.ISetCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的set类型的操作
 */
@Repository
public class SetCacheImpl implements ISetCache {
    private static final Logger LOG = LogManager.getLogger("bizLog");

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public long count(final String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
           LOG.info("获取SET类型集合长度失败: key：【{}】", key);
           e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public long cacheSet(final String key, final String ... values) {
        try {
            //序列化key，否则用basicCache里面的delKey无法将其删除
            RedisSerializer<String> serializer = new StringRedisSerializer();
            redisTemplate.setKeySerializer(serializer);
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            LOG.error("Redis的cacheSet失败: key：【{}】; values：【{}】;", key, values);
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public Set<String> getSet(final String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            LOG.error("Redis的getSet失败: key：【{}】", key);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public long removeSet(final String key, final String... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            LOG.error("Redis的removeSet失败：key：【{}】", key);
            e.printStackTrace();
        }
        return -1L;
    }

    @Override
    public boolean exists(String key, String value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            LOG.error("判断元素是否存在于set集合中：key：【{}】，value：【{}】", key, value);
            e.printStackTrace();
        }
        return false;
    }
}
