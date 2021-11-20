package com.jun.plugin.freemarker.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FreemarkerController3 {
	@RequestMapping(value="/free3")
	public ModelAndView free3(){
		ModelAndView mv3 = new ModelAndView();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("Java","你好Java");
		map.put("address","北京");
		map.put("身高",172);
		map.put("money", 100.5);
		mv3.addObject("map",map);
		return mv3;
	}

}

//http://localhost:8080/free3