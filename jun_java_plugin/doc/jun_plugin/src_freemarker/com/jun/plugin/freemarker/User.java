package com.jun.plugin.freemarker;

/**
 * 
 *  用户信息
 * @author hailang
 * @date 2009-9-9 上午09:13:51
 * @version 1.0
 */
public class User {
	private String userName;
	private String userPassword;
	private Integer age;
	
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
