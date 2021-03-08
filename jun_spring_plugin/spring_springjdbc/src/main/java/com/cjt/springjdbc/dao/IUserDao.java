package com.cjt.springjdbc.dao;

import java.util.List;

import org.springframework.jdbc.support.KeyHolder;

import com.cjt.springjdbc.model.User;

public interface IUserDao {
	
	//根据ID查找一个用户
	User findById(int id);

	//返回所有用户
	List<User> findAll();

	//新增一个用户
	KeyHolder save(User user);

	//更新一个用户
	void update(User user);

	//删除一个用户
	void delete(int id);
}
