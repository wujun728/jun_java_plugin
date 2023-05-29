package com.jun.web.biz.domain;
/*
 * 
 * 	ʵ��bean user��
 * 
create table users(
   id int primary key auto_increment,
   username varchar(40),
   password varchar(60),
   email varchar(40),
   nickname varchar(40),
   status int,
   regsittime timestamp,
   activecode varchar(100)
);  
 * 
 * 
 * 
 * 
 */
public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private String nickname;
	private String role;  // ��ɫ
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	
}
