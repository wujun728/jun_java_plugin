package cn.itcast.mis.req.hibernate;

import java.io.Serializable;

public class UserT implements Serializable{

	private Integer userId;
	private String userName;
	private String userPassword;
	private String userEmail;
	
	public UserT() {
		super();
		// TODO Auto-generated constructor stub
	}

	public UserT(Integer userId, String userName, String userPassword,
			String userEmail) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
	}
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
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
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	
	
}
