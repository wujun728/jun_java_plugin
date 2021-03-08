package com.sam.demo.spring.boot.jpa.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sam.demo.AppStart;
import com.sam.demo.spring.boot.jpa.entity.User;
import com.sam.demo.spring.boot.jpa.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={AppStart.class})
public class TestUserService {
	
	@Autowired
	UserService userService;
	
	
	@Test
	public void findOneTest(){
//		User user = userService.findOne(1L);
//		Assert.assertEquals(user.getUsername(), "admin");
		
		List<User> list = userService.extendMethod("z","111@163.com");
		System.out.println(list.size());
	}
	
	@Test
	public void updateUserTest(){
		User user = userService.findOne(1L);
		user.setMobilePhoneNumber("13900000007");
		userService.updateUser(user);
	}
	
}
