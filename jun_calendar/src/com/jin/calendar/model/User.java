package com.jin.calendar.model;

import com.jfinal.plugin.activerecord.Model;

@SuppressWarnings("serial")
public class User extends Model<User> {
	public static final User dao = new User();
	
	public User getUserByLoginName(String loginName){
		return User.dao.findFirst("select * from user where loginName='"+loginName+"'");
	}
	
}
