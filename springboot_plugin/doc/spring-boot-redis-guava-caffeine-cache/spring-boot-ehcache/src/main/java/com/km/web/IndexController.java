package com.km.web;

import com.google.common.collect.ImmutableMap;
import com.km.entity.User;
import com.km.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p></p>
 * Created by zhezhiyong@163.com on 2017/9/21.
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<User> users() {
        return userService.list();
    }

    @GetMapping("/findUser/{id}")
    @ResponseBody
    public User findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @GetMapping("/updateUser/{id}")
    @ResponseBody
    public User updateUserById(@PathVariable("id") Long id) {
        User user = userService.findUserById(id);
        user.setName("updateUserById");
        userService.update(user);
        return user;
    }

    @GetMapping("/deleteUser/{id}")
    @ResponseBody
    public Map deleteUser(@PathVariable("id") Long id) {
        userService.remove(id);
        return ImmutableMap.of("ret", 0, "msg", "ok");
    }

}
