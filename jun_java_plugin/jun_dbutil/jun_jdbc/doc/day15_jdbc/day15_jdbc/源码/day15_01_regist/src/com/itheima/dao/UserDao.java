package com.itheima.dao;

import com.itheima.domain.User;

public interface UserDao {
	/**
	 * 根据用户名查询用户是否存在
	 * @param username
	 * @return 不存在返回null
	 */
	User findByUsername(String username);
	/**
	 * 保存用户信息
	 * @param user
	 */
	void save(User user);
	/**
	 * 根据用户名和密码查询用户
	 * @param username
	 * @param password
	 * @return 用户名或密码错误，返回null
	 */
	User findUser(String username, String password);

}
