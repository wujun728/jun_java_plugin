package com.citi.redis.service;

import com.citi.redis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LoginService {

    @Autowired
    StringRedisTemplate redisTemplate;

    public User check_token(String token){
        return (User) redisTemplate.opsForHash().get("login",token);
    }

    public void update_token(String token,User user){
        Date date=new Date();
        redisTemplate.opsForHash().put("login:",token,user);
        redisTemplate.opsForZSet().add("recent:",token,date.getTime());


    }
}
