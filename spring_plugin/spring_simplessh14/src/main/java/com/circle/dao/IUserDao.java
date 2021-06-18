package com.circle.dao;


import java.util.*;
import com.circle.entity.User;

public interface IUserDao {
	public List<User> listUsers();//查询所有用户
	public int getUsersCount();//返回用户数量
	public User findUserByName(String name);
	public void register(User user);//注册
	public int login(String name, String password);	//登录
	public void delete(User user);//删除
	public void update(User user);//修改
	public void add(User user);//添加
}

