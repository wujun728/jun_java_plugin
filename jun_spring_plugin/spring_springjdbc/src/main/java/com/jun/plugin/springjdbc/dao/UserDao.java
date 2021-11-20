package com.jun.plugin.springjdbc.dao;

import java.util.Collection;
import java.util.List;

import com.jun.plugin.springjdbc.beans.User;

public interface UserDao {

	//��ȡ���е��û�
	public List<User> getAllUsers();
	//����id��ȡ�û�
	public User getUserById(int id);
	//��������
	public int addUser(User user);
	//ɾ������
	public int deleteUserById(int id);
	//��������
	public int update(User user);
	
}
