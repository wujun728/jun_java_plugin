package com.ssmm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.ssmm.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring.xml",
		"classpath:spring-mybatis.xml" })
public class TestUserDao {
	private static final Logger logger = Logger
			.getLogger(TestUserDao.class);

	@Autowired
	private UserMapper userMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Test
	public void insert(){
		User user = new User();
		user.setAge(18);
		user.setUsername("test02");
		user.setPassword("123456");
		
		int id = userMapper.insert(user);
		System.out.println(id);
	}
	
	@Test
	public void getUserById() {
		User user = userMapper.selectByPrimaryKey(1);
		logger.info(JSON.toJSONString(user));
	}
	
	@Test
	public void getAll(){
		List<User> users = userMapper.getAll();
		logger.info(JSON.toJSONString(users));
	}
}
