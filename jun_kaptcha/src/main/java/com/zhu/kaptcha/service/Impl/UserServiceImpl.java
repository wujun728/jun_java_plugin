package com.zhu.kaptcha.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhu.kaptcha.dao.UserDao;
import com.zhu.kaptcha.entity.User;
import com.zhu.kaptcha.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public boolean login(String userName, String passWord) {
		User user = userDao.findUserByUserName(userName);
		if (passWord.equals(user.getPassWord())) {
			return true;
		} else {
			return false;
		}

	}

}
