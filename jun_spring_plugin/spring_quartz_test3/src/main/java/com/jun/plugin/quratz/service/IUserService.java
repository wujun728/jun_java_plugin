package com.jun.plugin.quratz.service;

import com.jun.plugin.quratz.entity.User;

public interface IUserService {

	public User getUserById(int id);
	
	public User getUser(User u);
}

