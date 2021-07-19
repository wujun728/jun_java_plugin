package com.buxiaoxia.business.aopDemo.service;

import com.buxiaoxia.business.aopDemo.annotation.LogAnnotation;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * Created by xw on 2017/3/25.
 * 2017-03-25 16:31
 */
@Service
public class HelloService {

	@LogAnnotation
	public User getUser(String name) {
		User user = new User();
		user.setAge(10);
		user.setName(name);
		return user;
	}

	@Data
	private class User {

		String name;
		int age;
	}

}