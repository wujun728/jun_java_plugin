package com.ssh2.service.impl;

import org.springframework.stereotype.Service;

import com.ssh2.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService{
	
	public String sayHello() {
		return "Hello World";
	}

}
