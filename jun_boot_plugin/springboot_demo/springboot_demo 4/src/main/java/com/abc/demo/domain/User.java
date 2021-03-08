package com.abc.demo.domain;

public class User {
	private Integer id;
	private String userName;
	private String gender;
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(Integer id, String userName, String gender) {
		super();
		this.id = id;
		this.userName = userName;
		this.gender = gender;
	}
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", gender=" + gender + "]";
	}
	
	
}
