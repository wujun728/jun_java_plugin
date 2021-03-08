package com.sam.demo.spring.boot.mybatis.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sam.demo.AppStart;
import com.sam.demo.spring.boot.mybatis.entity.User;
import com.sam.demo.spring.boot.mybatis.entity.mapper.UserMapper;
import com.sam.demo.spring.boot.mybatis.service.UserService;

import org.junit.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={AppStart.class})
public class TestUserService {
	
	@Autowired
	UserService userService;
	
	
	@Test
	public void findOneTest(){
		User user = userService.findOne(1L);
		Assert.assertEquals(user.getName(), "admin");
	}
	
	@Test
	public void updateUserTest(){
		User user = userService.findOne(1L);
		user.setMobilePhoneNumber("13900000009");
		userService.updateUser(user);
	}
}
