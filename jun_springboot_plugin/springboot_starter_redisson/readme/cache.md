package com.zengtengpeng.test.controller;

import com.zengtengpeng.test.bean.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CacheController {


    @RequestMapping("/cache1")
    @ResponseBody
    @Cacheable(value = "cache1",key = "'test1'")
    public String cache1(){
        return "test";
    }
    @RequestMapping("/cache2")
    @ResponseBody
    @Cacheable(value = "cache2",key = "'test2'")
    public User cache2(){
        User user=new User();
        user.setName("name");
        user.setAge("123");
        return user;
    }

}
