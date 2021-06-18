package com.itstyle.mongodb.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.itstyle.mongodb.model.Users;

public interface UserRepository extends MongoRepository<Users, Integer> {

	public Users findUserByName(String name);

	public void removeUser(String name);

	public void updateUser(String name, String key, String value);

}
