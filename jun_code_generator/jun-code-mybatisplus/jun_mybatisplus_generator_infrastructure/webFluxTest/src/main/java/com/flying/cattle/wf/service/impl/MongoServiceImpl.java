package com.flying.cattle.wf.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flying.cattle.wf.dao.UserRepository;
import com.flying.cattle.wf.entity.User;
import com.flying.cattle.wf.service.MongoService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class MongoServiceImpl implements MongoService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Mono<User> getById(Long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id);
	}

	@Override
	public Mono<User> addUser(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public Mono<Boolean> deleteById(Long id) {
		// TODO Auto-generated method stub
		 userRepository.deleteById(id);
		 return Mono.create(userMonoSink -> userMonoSink.success());
	}

	@Override
	public Mono<User> updateById(User user) {
		// TODO Auto-generated method stub
		return userRepository.save(user);
	}

	@Override
	public Flux<User> findAllUser() {
		// TODO Auto-generated method stub
		return userRepository.findAll();
	}
}
