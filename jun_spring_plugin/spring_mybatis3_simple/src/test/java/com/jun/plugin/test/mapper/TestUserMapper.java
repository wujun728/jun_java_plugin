package com.jun.plugin.test.mapper;

import java.util.List;

import com.jun.plugin.entity.UserEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@ContextConfiguration(locations = { "classpath:/test*.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
public class TestUserMapper {

	@Autowired
	private com.jun.plugin.mapper.UserMapper UserMapper;

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

