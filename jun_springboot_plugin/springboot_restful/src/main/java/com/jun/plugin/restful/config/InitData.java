package com.jun.plugin.restful.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.jun.plugin.restful.model.User;
import com.jun.plugin.restful.repository.UserRepository;

@Component
public class InitData implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;
    @Override
    public void run(ApplicationArguments args) {
        System.out.println("初始化");
        for(int i=0;i<10;i++){
            User user = new User();
            user.setUsername("张三"+i);
            user.setNickname("张三"+i);
            user.setEmail("100000@qq.com");
            user.setMobile("18105428888");
            user.setPassword("111111");
            userRepository.save(user);
        }
    }
}
