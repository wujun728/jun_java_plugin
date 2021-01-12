package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;
/*
use day15;
create table USER(
	USERNAME varchar(100) primary key,
	PASSWORD varchar(100) not null,
	EMAIL varchar(100) not null,
	BIRTHDAY date not null
);
 */
public class User implements Serializable {
	private String username;//Ψһ
	private String password;
	private String email;
	private Date birthday;
	
	public User(){}
	
	public User(String username, String password, String email, Date birthday) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.birthday = birthday;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
}
