package com.zhu.kaptcha.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhu.kaptcha.BaseTest;
import com.zhu.kaptcha.entity.User;

public class UserDaoTest extends BaseTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void testFindUserByUserName() {
		User user = userDao.findUserByUserName("林志玲");
		System.out.println(user.getPassWord());
	}
}
