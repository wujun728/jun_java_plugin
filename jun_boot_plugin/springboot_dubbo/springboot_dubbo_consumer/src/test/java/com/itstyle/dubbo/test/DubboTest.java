package com.itstyle.dubbo.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itstyle.dubbo.domain.User;
import com.itstyle.dubbo.service.IUserService;

@Component
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:dubbo.xml" })
//可以运行 自行修改 dubbo.xml中address
public class DubboTest {
	@Reference
	private IUserService userService;

	@Test
	public void pors() {
		System.out.println("开启");
		User user = new User("张三", 19);
		userService.saveUser(user);
	}
}
