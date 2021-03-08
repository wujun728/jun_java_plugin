package com.sam.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="users")
public class UserController {

	/**
	 * 请求转发
	 * @return
	 */
	@RequestMapping(value="index")
	public String forward(){
		return "index";
	}
	
	/**
	 * 重定向
	 * @return
	 */
	@RequestMapping(value="page")
	public String redirect(){
		return "redirect:/page.html";
	}
}
