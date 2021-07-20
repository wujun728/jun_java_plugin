package com.jun.plugin.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.springboot.config.MyConfigBean;

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
