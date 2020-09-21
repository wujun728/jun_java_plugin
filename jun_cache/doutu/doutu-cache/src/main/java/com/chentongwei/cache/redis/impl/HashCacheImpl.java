package com.chentongwei.cache.redis.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import com.chentongwei.cache.redis.IHashCache;

/**
 * Redis的Hash类型操作实现
 * 
 * @author TongWei.Chen 2017-5-21 14:13:38
 */
@Repository
public class HashCacheImpl implements IHashCache {
	
	private static final Logger LOG = LoggerFactory.getLogger(HashCacheImpl.class);

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public void cacheHash(final String key, final Object hashKey, final Object value) {
		try {
			//序列化key，否则用basicCache里面的delKey无法将其删除
			RedisSerializer<String> serializer = new StringRedisSerializer();
			redisTemplate.setKeySerializer(serializer);
			redisTemplate.opsForHash().put(key, hashKey, value);
		} catch (Exception e) {
			LOG.error("缓存到hash失败，key：" + key + "；hashKey：" + hashKey, e);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getHash(final String key, final Object hashKey) {
		try {
			return redisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			LOG.error("从Redis的hash中获取失败，key：" + key + "；hashKey：" + hashKey, e);
			e.printStackTrace();
		}
		return null;
	}
}