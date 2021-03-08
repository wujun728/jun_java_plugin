package com.zh.springbootjwt.controller;

import com.alibaba.fastjson.JSONObject;
import com.zh.springbootjwt.config.annotation.CurrentUser;
import com.zh.springbootjwt.config.annotation.Token;
import com.zh.springbootjwt.model.User;
import com.zh.springbootjwt.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JSONObject login(@NotBlank(message = "用户名不能为空") String username, @NotBlank(message = "密码不能为空") String password){
        return this.userService.login(username,password);
    }

    @Token
    @PostMapping("checkUserInfo")
    public User save(@CurrentUser User user){
        return user;
    }
}
