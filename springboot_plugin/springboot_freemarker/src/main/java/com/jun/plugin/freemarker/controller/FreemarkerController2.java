package com.jun.plugin.freemarker.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FreemarkerController2 {
	
	@RequestMapping("/free2")
	public ModelAndView free2(){
		ModelAndView mv2 = new ModelAndView();
		List<String> list = new ArrayList<String>();
		list.add("java");
		list.add("JavaScript");
		list.add("python");
		list.add("php");
		list.add("Html");
		mv2.addObject("myList",list);
		return mv2;
	}

}


//http://localhost:8080/free2