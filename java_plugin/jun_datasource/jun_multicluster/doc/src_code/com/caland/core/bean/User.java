package com.caland.core.bean;

import java.util.*;
import java.io.Serializable;

/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */
public class User implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private Integer age;
	private Integer phone;
	private String email;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public Integer getPhone() {
		return phone;
	}
	public void setPhone(Integer phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String toString() {
		return "User [id=" + id + ",username=" + username + ",age=" + age + ",phone=" + phone + ",email=" + email + "]";
	}
}
