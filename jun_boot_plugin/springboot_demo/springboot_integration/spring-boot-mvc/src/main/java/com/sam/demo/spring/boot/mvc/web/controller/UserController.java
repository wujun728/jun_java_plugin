package com.sam.demo.spring.boot.mvc.web.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/sam")
public class UserController {
	
	@RequestMapping(value="/show",method = {RequestMethod.POST,RequestMethod.GET})
	public String show(User user, HttpServletRequest request) {
		System.out.println(user.getName());
		return "abc";
	}
}
