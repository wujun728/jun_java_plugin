package com.zhu.kaptcha.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.zhu.kaptcha.BaseTest;

public class UserServiceTest extends BaseTest {
	
	@Autowired
	private UserService userService;
	
	@Test
	public void testLogin() {
		boolean result = userService.login("林志玲", "56789");
		System.out.println(result);
	}

}
