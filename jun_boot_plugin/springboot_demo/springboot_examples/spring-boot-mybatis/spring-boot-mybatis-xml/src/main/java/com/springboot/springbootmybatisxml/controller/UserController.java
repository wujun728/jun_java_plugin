package com.springboot.springbootmybatisxml.controller;

import com.springboot.springbootmybatisxml.mapper.UserMapper;
import com.springboot.springbootmybatisxml.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @Version: 1.0
 * @Desc:
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    UserMapper userMapper;

    @GetMapping("/getUsers")
    public List<User> getUsers() {
        return userMapper.getAll();
    }

    @GetMapping("/getUser/{id}")
    public User getUser(@PathVariable("id") String id) {
        return userMapper.getUser(id);
    }

    @PostMapping("/add")
    public Long save(@RequestBody User user) {
        user.setCreateDate(new Date());
        return userMapper.insertUser(user);
    }

    @PostMapping("/update")
    public Long update(@RequestBody User user) {
        return userMapper.updateUser(user);
    }

    @DeleteMapping("/delete/{id}")
    public Long delete(@PathVariable("id") String id) {
        return userMapper.deleteUser(id);
    }
}
