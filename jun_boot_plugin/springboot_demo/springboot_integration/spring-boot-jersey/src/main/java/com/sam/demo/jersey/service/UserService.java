package com.sam.demo.jersey.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.sam.demo.jersey.entity.User;
import com.sam.demo.jersey.repository.UserRepository;

@Component
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User findOne(Long id) {
		return userRepository.findOne(id);
	}
	
	@Transactional
	public User saveUser(User user){
		userRepository.save(user);
		return user;
	}
	
}
