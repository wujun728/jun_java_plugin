package com.jun.plugin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jun.plugin.dao.UsersDao;
import com.jun.plugin.entity.User;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping({"/users"})
public class UsersController {
    @Autowired
    private UsersDao userDao;

    @RequestMapping(value={""}, method={RequestMethod.GET})
    public List<User> list()
    {
        List<User> users = userDao.queryAll();
        return users;
    }
}
