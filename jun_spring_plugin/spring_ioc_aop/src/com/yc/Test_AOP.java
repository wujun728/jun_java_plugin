package com.yc;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test_AOP {
	public static void main(String[] args) {
		BeanFactory bf=new ClassPathXmlApplicationContext("spring-*.xml");
		
		UserDao userDao=(UserDao) bf.getBean("UserDao");
		
		
		System.out.println("-----");
		userDao.add();
		System.out.println("----");
		userDao.del();
		
		
	}
}
