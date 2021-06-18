package com.zhu.webservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zhu.webservice.service.ILoginService;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	ILoginService loginService;
	
	@RequestMapping("/login")
	public String showView() {
		System.out.print("45");
		return "helloWorld";
	}

}
