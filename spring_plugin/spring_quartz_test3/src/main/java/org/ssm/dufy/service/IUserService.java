package org.ssm.dufy.service;

import org.ssm.dufy.entity.User;

public interface IUserService {

	public User getUserById(int id);
	
	public User getUser(User u);
}

