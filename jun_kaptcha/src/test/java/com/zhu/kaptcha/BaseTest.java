package com.zhu.kaptcha;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zhu.kaptcha.service.UserService;

/**
 * 初始化spring各项配置的
 * @author zhu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml","classpath:spring/spring-service.xml"})
public class BaseTest {
	

	@Autowired
	private UserService userService;

	@Test
	public void login() {
		boolean result = userService.login("123", "123");
		assertEquals(true, result);

	}
	

}
