package com.wang.service.Impl;

import com.wang.dao.UserEntityDao;
import com.wang.domain.UserEntity;
import com.wang.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/2/11 0011.
 */
@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserEntityDao userDao;

    @Override
    public UserEntity findByname(String username) {
        return userDao.findByUsername(username) ;
    }
}
