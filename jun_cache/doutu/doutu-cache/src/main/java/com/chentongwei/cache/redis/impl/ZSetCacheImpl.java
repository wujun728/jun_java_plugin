package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.IZSetCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * Redis的zset类型的接口实现
 * 
 * @author TongWei.Chen 2017-5-30 10:34:38
 */
@Repository
public class ZSetCacheImpl implements IZSetCache {
	private static final Logger LOG = LoggerFactory.getLogger(ZSetCacheImpl.class);
	
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
			LOG.error("Redis的cacheZSet失败: key为" + key + ": value为" + value);
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
			LOG.error("Redis的getZSet失败: key为" + key);
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
			LOG.error("Redis的ZSet的size失败: key为" + key);
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
			LOG.error("Redis的ZSet的remove失败: key为" + key + ": value为" + value);
		}
		return 0;
	}

}
