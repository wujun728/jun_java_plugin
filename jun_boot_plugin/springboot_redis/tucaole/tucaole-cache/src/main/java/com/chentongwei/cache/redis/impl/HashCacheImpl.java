package com.chentongwei.cache.redis.impl;

import com.chentongwei.cache.redis.IHashCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: Redis的Hash类型操作实现
 */
@Repository
public class HashCacheImpl implements IHashCache {
	
	private static final Logger LOG = LogManager.getLogger("bizLog");

	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	@SuppressWarnings("unchecked")
	@Override
	public void cacheHash(final String key, final Object hashKey, final Object value) {
		try {
			//序列化key,value，这样存到redis的hash里面不会出现乱码。就可以进行删除key和根据key获取value
			RedisSerializer<String> serializer = new StringRedisSerializer();
			redisTemplate.setKeySerializer(serializer);
			redisTemplate.setHashKeySerializer(serializer);
			redisTemplate.setHashValueSerializer(serializer);
			redisTemplate.opsForHash().put(key, hashKey, value);
		} catch (Exception e) {
			LOG.error("缓存到hash失败，key：【{}】; hashKey：【{}】;", key, hashKey);
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object getHash(final String key, final Object hashKey) {
		try {
			return redisTemplate.opsForHash().get(key, hashKey);
		} catch (Exception e) {
			LOG.error("从Redis的hash中获取失败，key：【{}】; hashKey：【{}】;", key, hashKey);
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long deleteHash(String key, Object... hashKeys) {
		try {
			return redisTemplate.opsForHash().delete(key, hashKeys);
		} catch (Exception e) {
			LOG.error("从Redis的hash中删除失败，key：【{}】; hashKeys：【{}】;", key, hashKeys.toString());
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public Map<Object, Object> entries(String key) {
		try {
			return redisTemplate.opsForHash().entries(key);
		} catch (Exception e) {
			LOG.error("从Redis的hash中查询全部数据失败，key：【{}】", key);
			e.printStackTrace();
		}
		return null;
	}
}