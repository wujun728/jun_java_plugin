/*   
 * Project: OSMP
 * FileName: UserServiceTest.java
 * version: V1.0
 */
package com.osmp.web.test;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.osmp.web.user.service.UserService;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月26日 上午9:57:22
 */

public class UserServiceTest extends BaseJunit4Test {

	@Autowired
	private UserService userService;

	@Test
	public void testGetUser() throws Exception {

	}

}
