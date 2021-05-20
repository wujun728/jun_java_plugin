package com.itheima.domain;

import java.io.Serializable;
import java.util.Date;
/*
use day15;
create table CUSTOMERS(
	ID varchar(100) primary key,
	NAME varchar(100),
	GENDER varchar(10),
	BIRTHDAY date,
	PHONENUM varchar(100),
	EMAIL varchar(100),
	HOBBY varchar(100),
	TYPE varchar(100),
	DESCRIPTION varchar(100)
);
 */
public class Customer implements Serializable {
	private String id;//GUID
	private String name;
	private String gender;// male female
	private Date birthday;
	private String phonenum;
	private String email;
	private String hobby;
	private String type;
	private String description;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getPhonenum() {
		return phonenum;
	}
	public void setPhonenum(String phonenum) {
		this.phonenum = phonenum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
