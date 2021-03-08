package com.java1234.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/freemarker")
public class HelloWorldFreemarkerController {

	@RequestMapping("/say")
	public ModelAndView say(){
		ModelAndView mav=new ModelAndView();
		mav.addObject("message", "springboot大爷你好！");
		mav.setViewName("helloWorld");
		return mav;
	}
}
