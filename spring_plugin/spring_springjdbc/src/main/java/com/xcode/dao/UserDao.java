package com.xcode.dao;

import java.util.Collection;
import java.util.List;

import com.xcode.beans.User;

public interface UserDao {

	//获取所有的用户
	public List<User> getAllUsers();
	//根据id获取用户
	public User getUserById(int id);
	//增加数据
	public int addUser(User user);
	//删除数据
	public int deleteUserById(int id);
	//更新数据
	public int update(User user);
	
}
