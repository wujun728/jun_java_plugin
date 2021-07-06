package com.huan.study.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * user controller
 *
 * @author huan.fu 2021/6/17 - 下午2:00
 */
@RestController
public class UserController {

    @GetMapping("queryUserInfo")
    public String queryUserInfo() {
        return "查询用户信息";
    }
}
