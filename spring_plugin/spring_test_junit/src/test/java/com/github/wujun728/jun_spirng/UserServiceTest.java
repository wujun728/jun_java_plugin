package com.github.wujun728.jun_spirng;

import java.util.Date;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.*;

import com.github.wujun728.jun_spirng.domain.User;
import com.github.wujun728.jun_spirng.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.testng.Assert.*;

@ContextConfiguration("classpath*:/spring-context.xml")
public class UserServiceTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
    private UserService userService;

	@Test
	public void testHasMatchUser() {
		boolean b1 = userService.hasMatchUser("admin", "123456");
		boolean b2 = userService.hasMatchUser("admin", "1111");
		assertTrue(b1);
		assertTrue(!b2);
	}

	@Test
     public void testFindUserByUserName()throws Exception{
        for(int i =0; i< 100;i++) {
            User user = userService.findUserByUserName("admin");
            assertEquals(user.getUserName(), "admin");
        }

    }


	@Test
	public void testAddLoginLog() {
		User user = userService.findUserByUserName("admin");
		user.setUserId(1);
		user.setUserName("admin");
		user.setLastIp("192.168.12.7");
		user.setLastVisit(new Date());
		userService.loginSuccess(user);
	}
}
