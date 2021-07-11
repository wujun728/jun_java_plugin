package com.battcn.sample.extend.crypto.controller;

import com.battcn.boot.extend.configuration.crypto.annotation.Decrypt;
import com.battcn.sample.extend.crypto.entity.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author battcn
 */
@RestController
@RequestMapping("/users")
public class UserController {


    @Decrypt
    @PostMapping
    public void add(@RequestBody User user) {
        System.out.println(user.getName());
        System.out.println(user.getId());
    }


}
