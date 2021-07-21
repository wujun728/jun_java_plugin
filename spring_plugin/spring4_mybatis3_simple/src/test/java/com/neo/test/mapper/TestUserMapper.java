package com.neo.test.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.neo.entity.UserEntity;
import com.neo.mapper.UserMapper;

@ContextConfiguration(locations = { "classpath:/test*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserMapper {

	@Autowired
	private UserMapper UserMapper;

	@Test
	public void testInsert() throws Exception {
		UserMapper.insert(new UserEntity("aa", "a123456"));
		UserMapper.insert(new UserEntity("bb", "b123456"));
		UserMapper.insert(new UserEntity("cc", "b123456"));

	}

	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = UserMapper.getAll();
		if(users==null || users.size()==0){
			System.out.println("is null");
		}else{
			System.out.println(users.toString());
		}
	}
	
	
	@Test
	public void testUpdate() throws Exception {
		UserEntity user = UserMapper.getOne(6l);
		System.out.println(user.toString());
		user.setUserName("大大");
		UserMapper.update(user);
	}
}

