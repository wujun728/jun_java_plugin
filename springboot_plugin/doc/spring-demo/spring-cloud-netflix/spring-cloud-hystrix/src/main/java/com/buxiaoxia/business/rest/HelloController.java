package com.buxiaoxia.business.rest;

import com.buxiaoxia.business.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by xw on 2017/3/6.
 * 2017-03-06 13:52
 */
@RestController
@RequestMapping("/api/v1.0/hello")
public class HelloController {

	@Autowired
	private HelloService helloService;

	@RequestMapping(method = RequestMethod.GET)
	public String getHello() {
		return helloService.getHello();
	}

	@RequestMapping(value = "/async", method = RequestMethod.GET)
	public String getHelloAsync() throws ExecutionException, InterruptedException {
		Future<String> future = helloService.getHelloAsync();
		return future.get();
	}

}
