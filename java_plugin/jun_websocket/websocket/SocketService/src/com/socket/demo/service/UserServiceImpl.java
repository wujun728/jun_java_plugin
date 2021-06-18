package com.socket.demo.service;

import com.socket.demo.bean.User;


public class UserServiceImpl implements UserService{

	@Override
	public User getUser(String name, Integer age) {
		sleep();
		return new User(name,age);
	}

	@Override
	public Integer add(Integer a) {
		sleep();
		return a+1;
	}
	
	private void sleep(){
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
