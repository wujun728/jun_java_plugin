package com.jun.mvc.dao;

import com.jun.mvc.BaseTests;
import com.jun.mvc.dao.IUserDao;
import com.jun.mvc.entity.User;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoTest extends BaseTests {

    @Autowired
    private IUserDao userDao;

    @Test
    public void testFindAll() {
        List<User> users = userDao.findAll();
        //
        if(users != null) {
            users.forEach(System.out::println);
        }
    }
}
