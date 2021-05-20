package com.qunar.vacation.example.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.qunar.vacation.example.model.User;
import com.qunar.vacation.example.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	private UserService userService;
	
	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@RequestMapping("/show/{id}")
	public ModelAndView getUser(@PathVariable("id") Integer id){
		User user = userService.getUser(id);
		return new ModelAndView("/user/show", Collections.singletonMap("user", user));
	}
	
}
