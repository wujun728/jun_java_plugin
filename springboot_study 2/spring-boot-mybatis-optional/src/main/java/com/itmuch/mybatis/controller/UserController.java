package com.itmuch.mybatis.controller;

import com.itmuch.mybatis.entity.User;
import com.itmuch.mybatis.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Wujun
 */
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserMapper userMapper;

    @GetMapping("/{id}")
    public User findById(@PathVariable Long id) {
        return this.userMapper.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException("This user does not exit!"));
    }
}

