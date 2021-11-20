package com.jun.plugin.dao.impl;

import org.springframework.stereotype.Service;

import com.jun.plugin.dao.BaseDao;
import com.jun.plugin.dao.UserDao;
import com.jun.plugin.entity.UserEntity;
@Service ("userDao")
public class UseDaoImpl extends BaseDao implements UserDao {

    @Override
    public void updateUser(UserEntity user) {
	this.test1Update(user);
    }

}

