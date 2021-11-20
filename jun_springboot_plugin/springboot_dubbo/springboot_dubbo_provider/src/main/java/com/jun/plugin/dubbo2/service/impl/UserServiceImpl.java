package com.jun.plugin.dubbo2.service.impl;

import org.springframework.stereotype.Component;

import com.alibaba.dubbo.config.annotation.Service;
import com.jun.plugin.dubbo2.domain.User;
import com.jun.plugin.dubbo2.service.IUserService;
//注意这里使用Dubbo注解方式
@Component("userService")
@Service
public class UserServiceImpl implements IUserService {
	@Override
	public void saveUser(User user) {
		System.out.println("保存用户:"+user.getUsername());
	}
}
