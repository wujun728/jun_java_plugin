package com.lxx.springboot.controller;

import com.lxx.springboot.config.MyConfigBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 读取配置文件中的properties 属性值 类
 */
@RestController
@EnableConfigurationProperties(MyConfigBean.class)
public class UserController {

    @Autowired
    MyConfigBean myConfigBean;

    @GetMapping("/user/name")
    public String getUser(){
        return myConfigBean.getGreeting()+" >>>>"+myConfigBean.getName()+" >>>>"+ myConfigBean.getUuid()+" >>>>"+myConfigBean.getMax();
    }

}
