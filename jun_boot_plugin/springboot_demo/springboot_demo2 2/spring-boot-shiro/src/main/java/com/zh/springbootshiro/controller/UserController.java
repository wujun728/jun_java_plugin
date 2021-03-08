package com.zh.springbootshiro.controller;

import com.zh.springbootshiro.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * @author Wujun
 * @date 2019/6/3
 */
@Slf4j
@Validated
@RestController
public class UserController {

    @PostMapping("/login")
    public String login(@NotBlank(message = "用户名不能为空") String username, @NotBlank(message = "密码不能为空") String password){
        Subject subject = SecurityUtils.getSubject();
        subject.login(new UsernamePasswordToken(username, password));
        log.info("================登录成功===============");
        return "SUCCESS";
    }

    @GetMapping("/logout")
    public String logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "SUCCESS";
    }

    @GetMapping("/index")
    public String index(){
        return "欢迎您:" + ((User) SecurityUtils.getSubject().getPrincipal()).getUsername();
    }

    @GetMapping("/403")
    public String unauthorizedPage(){
        return "没有权限";
    }

    @PostMapping("/checkUserInfo")
    public User save(){
        return (User) SecurityUtils.getSubject().getPrincipal();
    }

    @PostMapping("/updateUser")
    @RequiresPermissions("改")
    public String updateUser(){
        return "修改成功!";
    }

    @PostMapping("/deleteUser")
    @RequiresRoles("超级管理员")
    public String deleteUser(){
        return "删除成功!";
    }

}
