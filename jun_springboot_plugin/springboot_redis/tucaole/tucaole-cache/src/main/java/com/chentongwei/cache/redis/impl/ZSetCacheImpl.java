package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.IZSetCache;
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
 * @Description: Redis的zset类型的接口实现
 */
@Repository
public class ZSetCacheImpl implements IZSetCache {
	private static final Logger LOG = LogManager.getLogger("bizLog");
	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;

	@SuppressWarnings("unchecked")
	@Override
	public boolean cacheZSet(final String key, final Object value, final double score) {
		try {
			//序列化key，否则用basicCache里面的delKey无法将其删除
			RedisSerializer<String> serializer = new StringRedisSerializer();
			redisTemplate.setKeySerializer(serializer);
			return redisTemplate.opsForZSet().add(key, value, score);
		} catch (Exception e) {
			LOG.error("Redis的cacheZSet失败: key：【{}】; value：【{}】;", key, value);
			e.printStackTrace();
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<Object> getZSet(String key, long start, long end) {
		try {
			return redisTemplate.opsForZSet().reverseRange(key, start, end);
		} catch (Exception e) {
			LOG.error("Redis的getZSet失败: key：【{}】", key);
			e.printStackTrace();
		}		
		return null;
	}

	@Override
	public Set<Object> getZSet(String key) {
		return getZSet(key, 0L, -1L);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long zCard(final String key) {
		try {
			return redisTemplate.opsForZSet().zCard(key);
		} catch (Exception e) {
			LOG.error("Redis的ZSet的size失败: key：【{}】", key);
			e.printStackTrace();
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public long remove(final String key, final Object value) {
		try {
			return redisTemplate.opsForZSet().remove(key, value);
		} catch (Exception e) {
			LOG.error("Redis的ZSet的remove失败: key：【{}】; value：【{}】;", key, value);
		}
		return 0;
	}

}
