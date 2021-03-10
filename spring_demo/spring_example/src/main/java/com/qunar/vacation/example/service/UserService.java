package com.qunar.vacation.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.qunar.vacation.example.dao.UserDao;
import com.qunar.vacation.example.model.User;

@Service
public class UserService {
	@Autowired
	private UserDao userDao;
	
	@Autowired
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	public User getUser(Integer id){
		return userDao.get(id);
	}
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserService userService = context.getBean(UserService.class);
		User user = userService.getUser(10);
		System.out.println(user.getName());
	}
}
