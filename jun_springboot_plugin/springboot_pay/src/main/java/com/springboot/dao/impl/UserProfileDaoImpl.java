package com.springboot.dao.impl;

import org.springframework.stereotype.Repository;

import com.springboot.dao.BaseDao;
import com.springboot.dao.IUserProfileDao;
import com.springboot.model.UserProfile;

@Repository
public class UserProfileDaoImpl extends BaseDao<UserProfile> implements IUserProfileDao {

	@Override
	public void add(UserProfile userProfile) {
		save(userProfile);

	}

}
