package com.ssh2.web.action.tests;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;
import com.ssh2.service.HelloService;

@Controller
public class TestAction extends ActionSupport{
	@Autowired
	private HelloService helloService;
	
	private String message;

	public String getMessage() {
		return message;
	}

	@Action("showTest")
	public String showTest() {
		message = helloService.sayHello();
		return SUCCESS;
	}
}
