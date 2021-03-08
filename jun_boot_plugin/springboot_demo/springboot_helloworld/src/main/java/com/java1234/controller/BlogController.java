package com.java1234.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/blog")
public class BlogController {

	@RequestMapping("/{id}")
	public ModelAndView show(@PathVariable("id") Integer id){
		ModelAndView mav=new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("blog");
		return mav;
	}
	
	@RequestMapping("/query")
	public ModelAndView query(@RequestParam(value="q",required=false)String q){
		ModelAndView mav=new ModelAndView();
		mav.addObject("q", q);
		mav.setViewName("query");
		return mav;
	}
}
