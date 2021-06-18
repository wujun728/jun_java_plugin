package com.springmvc.model;


/**
 * 用户表
 * @author fenghaifeng
 * 2014年2月11日
 */
public class User {

	private int id;
	private String userName;
	private int userAge;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	
	
}
