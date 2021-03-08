package com.zh.springbootaoplog.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootaoplog.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String hello(String name){
        return "Hello World " + name;
    }

    @PostMapping("save")
    public String save(User user){
        return JSONObject.toJSONString(user);
    }
}
