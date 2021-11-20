package com.jun.plugin.servlet.guice.user.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.jun.plugin.servlet.guice.user.dao.UserDao;
import com.jun.plugin.servlet.guice.user.entity.User;
import com.jun.plugin.servlet.guice.user.service.UserService;

@Singleton
public class UserServiceImpl implements UserService {
	
	
	@Inject
	private UserDao userDao;

	@Override
	public void add(User u) throws Exception {
	
		userDao.add(u.getAccount(), u.getUser_id());
	}

	@Override
	public void addTwoUser(List<User> ulist) throws Exception {
		for(User u : ulist){
			add(u);
		}
		userDao.addTest();
		
	}
}
