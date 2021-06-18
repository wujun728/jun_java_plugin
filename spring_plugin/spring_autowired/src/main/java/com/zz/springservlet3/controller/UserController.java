package com.zz.springservlet3.controller;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import com.zz.springservlet3.entity.User;
import com.zz.springservlet3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
@Api(value = "/user", basePath = "/", description = "用户操作", produces = "application/json; charset=utf-8", hidden = true)
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping
    @ApiOperation(value = "获取所有用户", httpMethod = "GET", response = User.class, responseContainer = "Array", notes = "获取所有用户详情")
    public List<User> list() {
        return userService.findAll();
    }

    @RequestMapping("query")
    @ApiOperation(value = "查询", response = User.class, responseContainer = "Array", notes = "查询用户")
    public List<User> query(User user) {
        System.out.println(user.getUsername());
        return userService.findAll();
    }

    @RequestMapping(value = "{id}")
    @ApiOperation(value = "获取用户", httpMethod = "GET", response = User.class, notes = "获取用户详情")
    public User get(@ApiParam(required = true, name = "id", value = "用id") @PathVariable("id") Long id) {
        return userService.getOne(id);
    }

    @RequestMapping(value = "map")
    @ApiOperation(value = "map测试", httpMethod = "GET", response = Map.class, notes = "map测试")
    public Map<String, String> map(@ApiParam(required = true, name = "key", value = "key") @RequestParam("key") String key) {
        Map<String, String> map = new HashMap<>();
        map.put("key", key);
        return map;
    }
}
