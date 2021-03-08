package com.sam.demo.spring.boot.jpa;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.sam.demo.spring.boot.jpa.domain.service.UserService;


@SpringBootApplication
public class AppStart {

	public static void main(String[] args) throws SecurityException, IllegalStateException, NotSupportedException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		ApplicationContext beanFactory = SpringApplication.run(AppStart.class, args);
		UserService userService = beanFactory.getBean(UserService.class);
		
		userService.saveUser("scott");
	}
}
