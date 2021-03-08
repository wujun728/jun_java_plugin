package com.itstyle.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 科帮网  小柒 
 * http://www.52itstyle.com
 */
@Controller
public class HelloController {
	@RequestMapping("/hello")
    public String   greeting(ModelMap map) {
		map.addAttribute("name", "其实我是个演员");
		map.addAttribute("host", "http://blog.52itstyle.com");
        return "hello";
    }
}
