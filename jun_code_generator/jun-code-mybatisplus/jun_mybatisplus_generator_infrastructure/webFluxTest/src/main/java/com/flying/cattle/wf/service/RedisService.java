/**
 * @filename:User 2019年6月1日
 * @project webFlux-redis  V1.0
 * Copyright(c) 2018 BianPeng Co. Ltd. 
 * All right reserved. 
 */
package com.flying.cattle.wf.service;

import java.util.List;

import com.flying.cattle.wf.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**   
 * Copyright: Copyright (c) 2019 
 * 
 * <p>说明： 用户服务接口</P>
 * @version: V1.0
 * @author: BianPeng
 * 
 */
public interface RedisService {

	public Mono<Object> getById(String key);
	
	public Mono<Object> addUser(String key,User user);
	
	public Mono<Boolean> deleteById(String key);
	
	public Mono<Object> updateById(String key,User user);
	
	public Mono<Long> addlist(String key,List<Object> list);
	
	public Flux<Object> findAll(String key);

	public Flux<Object> findUsers(String key);
	
	
}
