package com.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.spring.method.SortMethod;

@Controller
@RequestMapping("/")
public class FreemarkerController4 {
	
	@RequestMapping("/free4")
	public ModelAndView free4(){
		ModelAndView mv4 = new ModelAndView();
		mv4.addObject("sort_int",new SortMethod());
		return mv4;
	}

}


//http://localhost:8080/free4
