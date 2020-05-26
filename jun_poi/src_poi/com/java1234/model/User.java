package com.java1234.model;

public class User {

	private int id;
	private String name;
	private String phone;
	private String email;
	private String qq;
	
	public User() {
	}
	
	public User(String name, String phone, String email, String qq) {
		this.name = name;
		this.phone = phone;
		this.email = email;
		this.qq = qq;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	
}
