package com.socket.demo.service;

import com.socket.demo.bean.User;

public interface UserService {
	
	public User getUser(String name,Integer age);
	
	public Integer add(Integer a);

}
