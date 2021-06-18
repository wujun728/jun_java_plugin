package com.itheima.test;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.itheima.domain.User;
import com.itheima.exception.UserHasExistException;
import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;

public class BusinessServiceImplTest {
	private BusinessService s = new BusinessServiceImpl();
	@Test
	public void testRegist() throws UserHasExistException {
		User user = new User("yyy", "123", "yyy@itcast.cn", new Date());
		s.regist(user);
	}
	@Test(expected=com.itheima.exception.UserHasExistException.class)
	public void testRegist1() throws UserHasExistException {
		User user = new User("wzhting", "123", "wzt@itcast.cn", new Date());
		s.regist(user);
	}
	@Test
	public void testLogin() {
		User user = s.login("wzhting", "123");
		assertNotNull(user);
		user = s.login("wzhting", "111");
		assertNull(user);
		user = s.login("asf", "111");
		assertNull(user);
	}

}
