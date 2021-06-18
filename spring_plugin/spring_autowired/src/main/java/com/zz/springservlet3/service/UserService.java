package com.zz.springservlet3.service;

import com.zz.springservlet3.dao.UserDao;
import com.zz.springservlet3.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
