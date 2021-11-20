package com.jun.plugin.servlet.guice.user.dao;

import java.sql.SQLException;

import com.google.inject.ImplementedBy;
import com.jun.plugin.servlet.guice.user.dao.impl.UserDaoImpl;

@ImplementedBy(UserDaoImpl.class)
public interface UserDao {
	void add(String name,int userId) throws SQLException;
	
	void addTest() throws SQLException;
}
