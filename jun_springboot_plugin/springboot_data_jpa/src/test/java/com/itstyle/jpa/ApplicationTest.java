package com.itstyle.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.itstyle.jpa.model.User;
import com.itstyle.jpa.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
	@Autowired
	private UserRepository userRepository;
	@Test
	public void test() throws Exception {
		User user = new User();
		user.setName("张三");
		user.setAge(20);
		userRepository.save(user);
	}
}
