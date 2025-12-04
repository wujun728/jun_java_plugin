package com.flying.cattle.wf.service;

import com.flying.cattle.wf.entity.User;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface MongoService {
	
	public Mono<User> getById(Long id);
	
	public Mono<User> addUser(User user);
	
	public Mono<Boolean> deleteById(Long id);
	
	public Mono<User> updateById(User user);
	
	public Flux<User> findAllUser();
}
