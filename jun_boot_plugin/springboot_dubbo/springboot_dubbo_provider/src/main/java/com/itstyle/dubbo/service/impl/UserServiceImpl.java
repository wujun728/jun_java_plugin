package com.itstyle.dubbo.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.itstyle.dubbo.domain.User;
import com.itstyle.dubbo.service.IUserService;
//注意这里使用Dubbo注解方式
@Component("userService")
@Service
public class UserServiceImpl implements IUserService {
	@Override
	public void saveUser(User user) {
		System.out.println("保存用户:"+user.getUsername());
	}
}
