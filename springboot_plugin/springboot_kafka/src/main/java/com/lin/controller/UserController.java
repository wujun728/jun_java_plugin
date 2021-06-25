package com.lin.controller;

import com.lin.model.User;
import com.lin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: LinZhaoguan
 * Date: 2018-03-15
 * Time: 23:16
 */
@RestController
@RequestMapping("api")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("user")
    public List<User> findAll() {
        return userService.findAll();
    }
}
