package com.mycompany.myproject.repository.redis.dao;

import com.mycompany.myproject.repository.redis.entity.MyRedisUser;
import org.springframework.data.repository.CrudRepository;

public interface MyRedisUserRepository extends CrudRepository<MyRedisUser,String> {

}
