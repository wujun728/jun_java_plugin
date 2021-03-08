package com.abc.demo.service;

import java.util.List;

import com.abc.demo.domain.User;

public interface UserService {
	//根据id查找用户
	User findById(Integer id);
	//查找全部用户
	List<User> findByAll();
	//新增用户
	int insert(User user);
	//根据ID删除用户
	int delete(Integer id);
	//更新用户
	int update(User user);
}
