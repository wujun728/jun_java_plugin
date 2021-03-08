package com.springboot.springbootjpahikari.controller;

import com.springboot.springbootjpahikari.model.UserModel;
import com.springboot.springbootjpahikari.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    /**
     * 查询用户列表
     * @return
     */
    @GetMapping("/user")
    public List<UserModel> user() {
        return userRepository.findAll(Sort.by("id").descending());
    }

    /**
     * 新增或更新用户信息
     * @param user
     * @return
     */
    @PostMapping("/user")
    public UserModel user(@RequestBody UserModel user) {
        return userRepository.save(user);
    }

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    @DeleteMapping("/user")
    public String deleteUserById(String id) {
        userRepository.deleteById(id);
        return "success";
    }
}
