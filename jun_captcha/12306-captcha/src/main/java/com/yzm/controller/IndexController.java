package com.yzm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	@RequestMapping(value = "/")
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("/yzm/yzm");
		return mv;
	}

	@RequestMapping(value = "/12306")
	public ModelAndView yzmPj() {
		return new ModelAndView("/yzm/12306");
	}

}
