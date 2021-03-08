package com.cjt.springjdbc.service;

import java.util.List;

import com.cjt.springjdbc.model.User;

public interface IUserService {
	
	//根据ID返回一个用户
	User findById(int id);
	
	//返回所有用户
	public List<User> findAll();
	
	//新增和更新用户
	public void saveOrUpdate(User user);
	
	//根据ID删除一个用户
	void delete(int id);
}
