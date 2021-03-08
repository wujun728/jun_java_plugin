package com.java1234.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ajax")
public class HelloWorldAjaxController {

	@RequestMapping("/hello")
	public String say(){
		return "{'message1':'SpringBoot大爷你好','message2','Spring大爷你好2'}";
	}
}
