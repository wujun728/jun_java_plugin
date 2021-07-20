package com.jun.plugin.mongodb2.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jun.plugin.mongodb2.model.Users;

public interface UserRepository extends MongoRepository<Users, Integer> {

	public Users findUserByName(String name);

	public void removeUser(String name);

	public void updateUser(String name, String key, String value);

}
