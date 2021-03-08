package com.springboot.springbootswagger.controller;

import com.springboot.springbootswagger.model.User;
import com.springboot.springbootswagger.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Version: 1.0
 * @Desc:
 */
@Api(value = "用户管理演示")
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/getUserById/{id}")
    @ApiOperation(value = "获取用户信息", notes = "根据用户 id 获取用户信息", tags = "查询用户信息类")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/getAllUsers")
    @ApiOperation(value = "获取全部用户信息", notes = "获取全部用户信息", tags = "查询用户信息类")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/saveUser")
    @ApiOperation(value = "新增/修改用户信息")
    public User saveUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("/deleteById")
    @ApiOperation(value = "删除用户信息", notes = "根据用户 id 删除用户信息")
    public String deleteById(@PathVariable Long id) {
        userService.deleteById(id);
        return "success";
    }
}
