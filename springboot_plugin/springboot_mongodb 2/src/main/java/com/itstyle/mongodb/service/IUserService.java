package com.itstyle.mongodb.service;

import java.util.List;

import com.itstyle.mongodb.model.Users;
/**
 * mongodb 案例
 * 创建者  小柒
 * 创建时间	2017年7月19日
 *
 */
public interface IUserService {
	public void saveUser(Users users);

	public Users findUserByName(String name);

	public void removeUser(String name);

	public void updateUser(String name, String key, String value);

	public List<Users> listUser();
}
