package com.mycompany.myproject.service;

import com.mycompany.myproject.repository.redis.dao.MyRedisUserRepository;
import com.mycompany.myproject.repository.redis.entity.MyRedisUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("myRedisUserService")
public class MyRedisuserServiceImpl implements MyRedisUserService {

    @Autowired
    private MyRedisUserRepository myRedisUserRepository;

    public void saveMyRedisUser(){

        MyRedisUser myRedisUser = new MyRedisUser();
        myRedisUser.setId("2009708");
        myRedisUser.setName("Barry Wang");

        myRedisUserRepository.save(myRedisUser);
    }
}
