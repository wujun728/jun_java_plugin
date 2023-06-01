package com.jun.plugin.springservlet3.test.service;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.jun.plugin.springservlet3.entity.User;
import com.jun.plugin.springservlet3.service.UserService;

import java.util.List;

public class UserServiceTest extends BaseServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void test_find_all() {
        List<User> list = userService.findAll();

        Assert.assertNotNull(list.get(0));
    }

}
