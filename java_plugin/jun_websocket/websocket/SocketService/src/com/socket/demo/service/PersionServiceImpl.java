package com.socket.demo.service;

import com.socket.demo.bean.User;


public class PersionServiceImpl implements PersionService {

	@Override
	public User getPersion(String name, Integer age) {
		return new User(name,age);
	}

}
