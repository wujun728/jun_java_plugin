package com.jun.user.entity;

/**
 * 用户实体
 * @author Administrator
 *
 */
public class User {

	private Integer id; // 编号
	private String userName; // 用户名
	private String password; // 密码
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
