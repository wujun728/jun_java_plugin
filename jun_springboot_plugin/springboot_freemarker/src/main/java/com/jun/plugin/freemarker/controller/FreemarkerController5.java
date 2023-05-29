package com.jun.plugin.freemarker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class FreemarkerController5 {
	
	@RequestMapping("/free5")
	public ModelAndView free5(){
		ModelAndView mv5 = new ModelAndView("free5");
		return mv5;
	}

}

//本例子不成功，报错，原因是我没有配置自定义指令的xml文件。
