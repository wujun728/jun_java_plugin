package com.sam.demo.service;

import org.springframework.stereotype.Service;

import com.sam.demo.entity.User;

@Service
public class UserService {

	public User findOne(Long id){
		User user = new User();
		user.setId(id);
		user.setUsername("scott");
		return user;
	}
}
