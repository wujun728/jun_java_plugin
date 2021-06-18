package com.xcode.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xcode.beans.User;
import com.xcode.dao.UserDao;
import com.xcode.server.UserServer;

@Service("UserServer")
public class UserServerImpl implements UserServer{

	@Autowired
	private UserDao userDao;
	
	public List<User> getAllUsers(){
		List<User> users=userDao.getAllUsers();
		return users;
	}
	
	@Override
	public User getUserById(int id) {
		User user=userDao.getUserById(id);
		return user;
	}

	@Override
	public int addUser(User user) {
	
		return 0;
	}

	@Override
	public int deleteUserById(int id) {
		
		return 0;
	}

}
