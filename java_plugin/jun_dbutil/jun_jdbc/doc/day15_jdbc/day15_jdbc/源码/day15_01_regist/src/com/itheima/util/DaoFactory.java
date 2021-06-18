package com.itheima.util;

import java.util.ResourceBundle;

import com.itheima.dao.UserDao;

public class DaoFactory {
	
	public static UserDao getUserDao(){
		try {
			ResourceBundle rb = ResourceBundle.getBundle("dao");
			String className = rb.getString("userDao");
			return (UserDao)Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
