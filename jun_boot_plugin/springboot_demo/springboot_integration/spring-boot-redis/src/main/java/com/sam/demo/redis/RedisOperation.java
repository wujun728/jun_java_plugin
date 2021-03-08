package com.sam.demo.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisOperation {
	
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	@Autowired
	private StringRedisTemplate stringRedisTemplate;
	
	public void saveValue(Object key,Object value){
		redisTemplate.opsForValue().set(key, value);
	}
	
	public Object getValue(Object key){
		return redisTemplate.opsForValue().get(key);
	}
	
	public void saveStringValue(String key,String value){
		stringRedisTemplate.opsForValue().set(key, value);
	}
	
	public String getStringValue(String key){
		return stringRedisTemplate.opsForValue().get(key);
	}

}
