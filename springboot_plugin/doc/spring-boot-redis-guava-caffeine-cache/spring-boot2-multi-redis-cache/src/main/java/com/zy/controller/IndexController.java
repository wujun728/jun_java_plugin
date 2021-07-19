package com.zy.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.ImmutableMap;
import com.zy.entity.User;
import com.zy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *     参考：https://blog.csdn.net/chenypgg/article/details/85698209
 * </p>
 * Created by zhezhiyong@163.com on 2017/9/21.
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @Resource(name = "redisTemplateB")
    private RedisTemplate redisTemplateB;

    @Resource(name = "redisTemplateA")
    private RedisTemplate redisTemplate;



    @GetMapping("/db/users")
    @ResponseBody
    public List<User> users3() {
        redisTemplate.opsForValue().set("usersa", JSON.toJSONString(userService.list()));
        redisTemplateB.opsForValue().set("usersb", JSON.toJSONString(userService.list()));
        return userService.list();
    }

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {
        return userService.list();
    }

    @GetMapping("/user/{id}")
    @ResponseBody
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/info/{id}")
    @ResponseBody
    public User findInfoById(@PathVariable("id") Long id) {
        return userService.findInfoById(id);
    }

    @GetMapping("/user/{id}/{name}")
    @ResponseBody
    public Map update(@PathVariable("id") Long id, @PathVariable("name") String name) {
        User user = userService.findUserById(id);
        user.setName(name);
        userService.update(user);
        return ImmutableMap.of("ret", 0, "msg", "ok");
    }


}
