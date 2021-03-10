/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.mockito.UserDaoTest.java
 * Class:			UserDaoTest
 * Date:			2012-12-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.mockito;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.Mockito;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-6 上午10:59:51 
 */

public class UserDaoTest {
	@Test
	public void test() {
		User user = new User();
		user.setId(1);
		user.setName("yao");
		user.setPassword("123456");

		UserDao userDao = Mockito.mock(UserDao.class);
		Mockito.when(userDao.get(1)).thenReturn(user);
		
		User resultUser = userDao.get(1);
		assertEquals(new Integer(1), resultUser.getId());
		Mockito.verify(userDao).get(1);
	}
}
