package com.springmvc.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springmvc.model.User;
import com.springmvc.service.UserService;

@Controller
public class MainController {

	@Resource(name="userService")
	private UserService userService;
	
	@RequestMapping("/test")
	public String index(User user){
		System.out.println(1234);
		System.out.println(user);
		return "index";
	}
	
	@RequestMapping("/fl")
	public ModelAndView fl(){
		ModelAndView mv = new ModelAndView("test");
		mv.addObject("userName", "test2222")
		  .addObject("userAge", 22);
		
		return mv;
	}
	
}
