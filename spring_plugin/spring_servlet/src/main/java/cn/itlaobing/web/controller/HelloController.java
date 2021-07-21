package cn.itlaobing.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.itlaobing.service.HelloService;

@Controller("/hello")
public class HelloController {
	
	Log log=LogFactory.getLog(HelloController.class);
	@Autowired
	private HelloService helloService;
	
	public void doGet(HttpServletRequest request,HttpServletResponse response){
		log.info(">>>>>>>>>>>>>>"+helloService.sayHello("spring"));
	}
	
}
