package com.jun.plugin.servlet.guice.user.service;


import java.util.List;

import com.google.inject.ImplementedBy;
import com.jun.plugin.servlet.guice.user.entity.User;
import com.jun.plugin.servlet.guice.user.service.impl.UserServiceImpl;

@ImplementedBy(UserServiceImpl.class)
public interface UserService {

	void add(User u) throws Exception;
	
	void addTwoUser(List<User> ulist) throws Exception;
}
