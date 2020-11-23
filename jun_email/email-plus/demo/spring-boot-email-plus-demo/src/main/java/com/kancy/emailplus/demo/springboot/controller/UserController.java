package com.kancy.emailplus.demo.springboot.controller;

import com.kancy.emailplus.demo.springboot.service.UserService;
import com.kancy.emailplus.spring.boot.aop.EmailNoticeTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * UserController
 *
 * @author Wujun
 * @date 2020/2/22 22:37
 */
@RestController
public class UserController {
    @Autowired
    List<UserService> list;
    @Autowired
    Map<String, EmailNoticeTrigger> map;
    @Autowired
    private UserService userService;
    @GetMapping("/user/exceptionTest")
    public String exceptionTest(){
        System.out.println(list);
        System.out.println(map);
        return userService.exceptionTest();
    }
    @GetMapping("/user/get")
    public String getUser(){
        return userService.getUser();
    }

    @ExceptionHandler(Exception.class)
    public String handleException(Exception e){
        return e.getMessage();
    }
}
