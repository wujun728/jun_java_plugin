package com.jun.plugin.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jun.plugin.entity.User;
import com.jun.plugin.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "/applicationContext.xml"})
public class UserServiceImplTest {
    @Autowired
    private IUserService iUserService;

    @Test
    public void testListUsers() throws Exception {
        User user = new User();
        user.setName("xiaoming");
        user.setPassword("123456");
        iUserService.add(user);
        System.out.println(user);
    }
}