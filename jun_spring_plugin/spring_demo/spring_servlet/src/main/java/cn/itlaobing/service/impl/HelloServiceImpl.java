package cn.itlaobing.service.impl;

import org.springframework.stereotype.Service;

import cn.itlaobing.service.HelloService;

@Service
public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		return "你好"+name;
	}

}
