package com.zz.springservlet3.test.service;


import com.zz.springservlet3.entity.User;
import com.zz.springservlet3.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
