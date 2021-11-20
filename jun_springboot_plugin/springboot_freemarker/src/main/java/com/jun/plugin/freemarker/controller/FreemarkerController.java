package com.jun.plugin.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FreemarkerController {
  
	@RequestMapping(value="/index")
	public ModelAndView index(){
		ModelAndView mv = new ModelAndView();
		mv.addObject("username","你好！Freemarker!");
		return mv;
	}
	
	@RequestMapping(value="/free6")
	public ModelAndView free6(){
		ModelAndView mv6 = new ModelAndView();
		return mv6;
	}
	
	@RequestMapping(value="/free7")
	public ModelAndView free7(){
		ModelAndView mv7 = new ModelAndView();
		return mv7;
	}
	
}

//http://localhost:8080/index
