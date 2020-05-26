package com.zhu.kaptcha.dao;

import com.zhu.kaptcha.entity.User;

public interface UserDao {
	User findUserByUserName(String userName);

}
