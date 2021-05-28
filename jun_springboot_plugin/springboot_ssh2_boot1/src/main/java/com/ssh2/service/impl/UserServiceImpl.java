package com.ssh2.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssh2.dao.UserDAO;
import com.ssh2.po.User;
import com.ssh2.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDAO userDao;

	@Override
	public List<User> find(String name) {
		return userDao.findAll();
	}


}
