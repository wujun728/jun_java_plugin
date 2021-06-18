package com.circle.service;

import com.circle.entity.User;

import java.util.List;

public interface IUserService {			//Service接口,将会在Service层添加事务
    public List<User> listUsers();		//列出所有的User
    public int getUsersCount();			//查询User数量
    public boolean register(User user); //注册
    public boolean login(String name,String password);//登录
    public boolean delete(User user);	//删除
    public boolean update(User user);	//修改
    public boolean add(User user); 		//添加
}