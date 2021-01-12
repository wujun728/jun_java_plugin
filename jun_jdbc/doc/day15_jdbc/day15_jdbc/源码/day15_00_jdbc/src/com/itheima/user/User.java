package com.itheima.user;

import java.io.Serializable;
import java.util.Date;
//实体Bean：Enity JavaBean
//Java类型和数据库类型：对应关系。相对固定的
public class User implements Serializable {
	private int id;
	private String name;
	private String password;
	private String email;
	private Date birthday;
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
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password
				+ ", email=" + email + ", birthday=" + birthday + "]";
	}
	
}
