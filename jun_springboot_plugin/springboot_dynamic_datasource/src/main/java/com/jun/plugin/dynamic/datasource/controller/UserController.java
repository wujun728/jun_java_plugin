package com.jun.plugin.dynamic.datasource.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.dynamic.datasource.mapper.UserMapper;
import com.jun.plugin.dynamic.datasource.model.User;

import java.util.List;

/**
 * <p>
 * 用户 Controller
 * </p>
 *
 * @author Wujun
 * @date Created in 2019/9/4 16:40
 */
@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserController {
    private final UserMapper userMapper;

    /**
     * 获取用户列表
     */
    @GetMapping("/user")
    public List<User> getUserList() {
        return userMapper.selectAll();
    }

}
