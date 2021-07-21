package com.zhm.spring.jsonview.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.zhm.spring.jsonview.model.Message;
import com.zhm.spring.jsonview.model.UserInfo;
import com.zhm.spring.jsonview.model.View;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhm on 2015/7/13.
 */
@RestController
public class HomeController {
    @RequestMapping(value="/home/sayHello/{username}", method = RequestMethod.GET)
    public Message sayHello(@PathVariable String username){
        return new Message(username);
    }

    /**
     * @JsonView(View.UserInfoWithOutPassword.class)  不返回密码
     * @JsonView(View.UserInfoWithPassword.class)  返回密码
     * @return
     */
    @RequestMapping(value="/home/showUser",method = RequestMethod.GET)
    public @JsonView(View.UserInfoWithOutPassword.class)UserInfo showUser(){
        return new UserInfo(1,"zhm","123456");
    }

}
