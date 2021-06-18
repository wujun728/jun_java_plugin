package com.circle.service;

import com.circle.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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