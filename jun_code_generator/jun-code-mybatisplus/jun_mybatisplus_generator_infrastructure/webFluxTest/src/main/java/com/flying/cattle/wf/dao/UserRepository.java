package com.flying.cattle.wf.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.flying.cattle.wf.entity.User;

public interface UserRepository extends ReactiveMongoRepository<User, Long>{

}
