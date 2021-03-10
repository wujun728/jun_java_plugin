package com.xcode.server;

import java.util.List;

import com.xcode.beans.User;

public interface UserServer {

	public List<User> getAllUsers();
	public User getUserById(int id);
	public int addUser(User user);
	public int deleteUserById(int id);
}
