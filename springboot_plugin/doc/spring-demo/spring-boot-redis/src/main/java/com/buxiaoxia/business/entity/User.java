package com.buxiaoxia.business.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Created on 2017-02-19 22:04.
 * author xiaw
 */
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private String cardId;
	private String password;
	private Integer age;

	public User() {
	}

	public User(String name, String cardId, String password, Integer age) {
		this.name = name;
		this.password = password;
		this.cardId = cardId;
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		return true;
	}

	@Override
	public int hashCode() {
		return id;
	}
}
