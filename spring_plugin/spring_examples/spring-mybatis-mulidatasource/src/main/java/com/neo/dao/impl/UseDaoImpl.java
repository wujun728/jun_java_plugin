package com.neo.dao.impl;

import org.springframework.stereotype.Service;

import com.neo.dao.BaseDao;
import com.neo.dao.UserDao;
import com.neo.entity.UserEntity;
@Service ("userDao")
public class UseDaoImpl extends BaseDao implements UserDao {

    @Override
    public void updateUser(UserEntity user) {
	this.test1Update(user);
    }

}

