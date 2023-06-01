package com.springboot.dao.impl;

import org.springframework.stereotype.Repository;

import com.springboot.dao.BaseDao;
import com.springboot.dao.IUserDao;
import com.springboot.model.User;

@Repository
public class UserDaoimpl extends BaseDao<User> implements IUserDao {

	@Override
	public void add(User user) {
		save(user);
	}

}
