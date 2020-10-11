package com.dubbos.action;

import com.alibaba.dubbo.config.annotation.Reference;
import com.dubbos.entity.User;
import com.dubbos.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by qilong on 2016-04-10-0010.
 */
@Controller
@RequestMapping(value = "user")
public class UserAction {
    @Reference(interfaceClass = UserService.class, version = "0.0.1")
    private UserService userService;

    @RequestMapping(value = "list")
    @ResponseBody
    public Object list(){
        List<User> userList = userService.findAllUser();
        return userList;
    }
}
