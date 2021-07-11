package com.buxiaoxia.business.entity;

import javax.persistence.*;

/**
 * Created on 2017-02-19 22:04.
 * author xiaw
 */
@Entity
public class User {

	private int id;
	private String name;
	private String password;
	private Integer age;

	public User() {
	}

	public User(String name, String password, Integer age) {
		this.name = name;
		this.password = password;
		this.age = age;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Basic
	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Basic
	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Basic
	@Column(name = "age")
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (name != null ? !name.equals(user.name) : user.name != null) return false;
		if (password != null ? !password.equals(user.password) : user.password != null) return false;
		if (age != null ? !age.equals(user.age) : user.age != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (password != null ? password.hashCode() : 0);
		result = 31 * result + (age != null ? age.hashCode() : 0);
		return result;
	}
}
