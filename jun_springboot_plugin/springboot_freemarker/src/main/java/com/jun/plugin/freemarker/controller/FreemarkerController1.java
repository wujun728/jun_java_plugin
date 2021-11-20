package com.jun.plugin.freemarker.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FreemarkerController1 {
	
	//freemarker取值，插值
	@RequestMapping("/free1")
	public ModelAndView free1(){
		ModelAndView mv1 = new ModelAndView();
		mv1.addObject("intVar",100);
		mv1.addObject("LongVar",10000000000000000L);
		mv1.addObject("doubleVar",3.141592675d);
		mv1.addObject("StringVar","我是freemarker,是字符串！");
		mv1.addObject("booleanVar", true);
		mv1.addObject("dateVar1",new Date());
		mv1.addObject("nullVar1",null);
		mv1.addObject("nullVar","我是空");
		return mv1;
	}

}

//http://localhost:8080/free1
