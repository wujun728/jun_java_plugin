package com.jun.plugin.springjdbc.server;

import java.util.List;

import com.jun.plugin.springjdbc.beans.User;

public interface UserServer {

	public List<User> getAllUsers();
	public User getUserById(int id);
	public int addUser(User user);
	public int deleteUserById(int id);
}
