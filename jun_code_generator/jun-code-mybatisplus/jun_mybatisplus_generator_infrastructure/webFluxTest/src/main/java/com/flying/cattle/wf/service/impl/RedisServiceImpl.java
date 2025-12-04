/**
 * @filename:User 2019年6月1日
 * @project webFlux-redis  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveListOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.core.ReactiveValueOperations;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.RedisService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Copyright: Copyright (c) 2019
 * 
 * <p>
 * 说明： 用户服务接口
 * </P>
 * 
 * @version: V1.0
 * @author: BianPeng
 * 
 */
@Service
public class RedisServiceImpl implements RedisService {

	@Autowired
	private ReactiveRedisTemplate<String, Object> redisTemplate;
	@Override
	public Mono<Object> getById(String key) {
		// TODO Auto-generated method stub
		ReactiveValueOperations<String, Object> operations = redisTemplate.opsForValue();
		return operations.get(key);
	}

	@Override
	public Mono<Object> addUser(String key,User user) {
		// TODO Auto-generated method stub
		ReactiveValueOperations<String, Object> operations = redisTemplate.opsForValue();
		return operations.getAndSet(key, JSON.toJSONString(user));
	}

	@Override
	public Mono<Boolean> deleteById(String key) {
		// TODO Auto-generated method stub
		ReactiveValueOperations<String, Object> operations = redisTemplate.opsForValue();
		return operations.delete(key);
	}

	@Override
	public Mono<Object> updateById(String key,User user) {
		// TODO Auto-generated method stub
		ReactiveValueOperations<String, Object> operations = redisTemplate.opsForValue();
		return operations.getAndSet(key, JSON.toJSONString(user));
	}

	@Override
	public Flux<Object> findAll(String key) {
		// TODO Auto-generated method stub
		ReactiveListOperations<String, Object> operations = redisTemplate.opsForList();
		return operations.range(key, 0, -1);
	}


	@Override
	public Mono<Long> addlist(String key,List<Object> list) {
		// TODO Auto-generated method stub
		ReactiveListOperations<String, Object> operations = redisTemplate.opsForList();
		return operations.leftPushAll(key, list);
	}
	
	@Override
	public Flux<Object> findUsers(String key) {
		ReactiveValueOperations<String, Object> operations = redisTemplate.opsForValue();
		return redisTemplate.keys(key).flatMap(keyId ->operations.get(keyId));
	}
}
