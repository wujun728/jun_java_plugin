package com.itheima.service;

import com.itheima.domain.User;
import com.itheima.exception.UserHasExistException;

public interface BusinessService {
	/**
	 * 注册新用户
	 * @param user 封装了用户信息的user对象
	 * @throws UserHasExistException 如果用户名已经存在，抛出此异常
	 */
	void regist(User user) throws UserHasExistException;
	/**
	 * 用户登录
	 * @param username 用户名
	 * @param password 密码
	 * @return 用户名或密码错误，返回null
	 */
	User login(String username,String password);
}
