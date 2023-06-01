package com.jun.plugin.springjdbc.server.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jun.plugin.springjdbc.beans.User;
import com.jun.plugin.springjdbc.dao.UserDao;
import com.jun.plugin.springjdbc.server.UserServer;

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
