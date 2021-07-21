package com.jun.plugin.springservlet3.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jun.plugin.springservlet3.dao.UserDao;
import com.jun.plugin.springservlet3.entity.User;

import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User getOne(Long id) {
        return userDao.findOne(id);
    }
}
