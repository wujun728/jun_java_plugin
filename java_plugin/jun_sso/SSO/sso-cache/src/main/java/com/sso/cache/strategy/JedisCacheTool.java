package com.sso.cache.strategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采用Pipeline管道模式提高批量插入效率
 * 
 */
@Component
public class JedisCacheTool {
	@Autowired
	private JedisPool jedisPool;
	private static JdkSerializationRedisSerializer redisSerializer = new JdkSerializationRedisSerializer();

	/**
	 * 批量插入
	 * 
	 * @param keys
	 * @param values
	 */
	public void putBatch(List<CacheObject> cacheObjects) {
		Jedis jedis = jedisPool.getResource();
		Pipeline pipeline = jedis.pipelined();
		for (CacheObject cacheObject : cacheObjects) {
			pipeline.set(redisSerializer.serialize(cacheObject.getKey()),
					redisSerializer.serialize(cacheObject.getValue()));
		}
		pipeline.sync();
		jedis.close();
	}

	/**
	 * 批量获取
	 * 
	 * @param keys
	 * @return
	 */
	public Map<Object, Object> getBatch(List<Object> keys) {
		Map<Object, Object> map = new HashMap<Object, Object>();
		Jedis jedis = jedisPool.getResource();
		Pipeline pipeline = jedis.pipelined();
		List<Response<byte[]>> results = new ArrayList<>();
		for (Object key : keys) {
			Response<byte[]> result = pipeline.get(redisSerializer.serialize(key));
			results.add(result);
		}
		pipeline.sync();
		for (int i = 0; i < results.size(); i++) {
			Response<byte[]> result = results.get(i);
			Object obj = redisSerializer.deserialize(result.get());
			if (obj != null)
				map.put(keys.get(i), obj);
		}
		jedis.close();
		return map;
	}

	/**
	 * 批量删除
	 * 
	 * @param keys
	 * @return
	 */
	public void deleteBatch(List<Object> keys) {
		Jedis jedis = jedisPool.getResource();
		Pipeline pipeline = jedis.pipelined();
		pipeline.del(redisSerializer.serialize(keys));
		pipeline.sync();
		jedis.close();
	}

	/**
	 * 单个获取
	 * 
	 * @param key
	 * @return
	 */
	public Object get(Object key) {
		Jedis jedis = jedisPool.getResource();
		Pipeline pipeline = jedis.pipelined();
		Response<byte[]> result = pipeline.get(redisSerializer.serialize(key));
		pipeline.sync();
		Object obj = redisSerializer.deserialize(result.get());
		jedis.close();
		return obj;
	}

	/**
	 * 单个插入
	 * 
	 * @param keys
	 * @param values
	 */
	public void put(Object key, Object value) {
		Jedis jedis = jedisPool.getResource();
		Pipeline pipeline = jedis.pipelined();
		pipeline.set(redisSerializer.serialize(key), redisSerializer.serialize(value));
		pipeline.sync();
		jedis.close();
	}

	/**
	 * 单个获取
	 * 
	 * @param key
	 * @return
	 */
	public void delete(Object key) {
		Jedis jedis = jedisPool.getResource();
		Pipeline pipeline = jedis.pipelined();
		pipeline.del(redisSerializer.serialize(key));
		pipeline.sync();
		jedis.close();
	}
}
