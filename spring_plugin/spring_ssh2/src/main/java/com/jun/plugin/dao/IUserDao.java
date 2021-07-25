package com.jun.plugin.dao;


import java.util.*;

import com.jun.plugin.entity.User;

public interface IUserDao {
	public List<User> listUsers();//��ѯ�����û�
	public int getUsersCount();//�����û�����
	public User findUserByName(String name);
	public void register(User user);//ע��
	public int login(String name, String password);	//��¼
	public void delete(User user);//ɾ��
	public void update(User user);//�޸�
	public void add(User user);//���
}

