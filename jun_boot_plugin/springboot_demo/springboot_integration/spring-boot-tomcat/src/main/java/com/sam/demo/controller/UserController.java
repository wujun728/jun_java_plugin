package com.sam.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.MoreObjects;
import com.sam.demo.entity.Book;
import com.sam.demo.entity.User;
import com.sam.demo.service.UserService;


@Controller
@RequestMapping(value="/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value="/show",method=RequestMethod.GET)
	@ResponseBody
	public String show(){
		User user = userService.findOne(1L);
		logger.info("username={}",user.getUsername());
		return user.getUsername();
	}
	
	@RequestMapping(value="/register",method=RequestMethod.GET)
	@ResponseBody
	public String register(User user){
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getAge());
		System.out.println(user.getBook().getName());
		System.out.println(user.getBook().getUsername());
		return "hello";
	}
	
}
