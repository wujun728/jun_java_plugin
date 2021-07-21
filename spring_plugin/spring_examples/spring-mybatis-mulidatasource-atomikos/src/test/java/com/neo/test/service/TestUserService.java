package com.neo.test.service;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neo.service.UserService;

@ContextConfiguration(locations = { "classpath:/test*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserService {
    
        @Resource
	private UserService userService;

	@Test
	public void testUpdateUserinfo() {
	    userService.updateUserinfo();
	    
	}
    

}

