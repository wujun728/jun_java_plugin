package com.abc.demo;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.abc.demo.domain.User;
import com.abc.demo.mapper.UserMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootDemoApplicationTests {
	@Autowired
	private UserMapper userMapper;
	
	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void testGetUserByName() {
		User user = userMapper.findById(1);
		System.out.println(user);
		userMapper.insert(new User(10,"沙僧","男"));
		
	}
	@Test
	public void testUpdate() {
		userMapper.update(new User(10,"如来","那女男"));
		User user = userMapper.findById(10);
		System.out.println(user);
	}
	
	@Test
	public void testDelAndGetALl() {
		userMapper.deleteById(10);
		List<User> list = userMapper.findByAll();
		System.out.println(list);
	}

}
