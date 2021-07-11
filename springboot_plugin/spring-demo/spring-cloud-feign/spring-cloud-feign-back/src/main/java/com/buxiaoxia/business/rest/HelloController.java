package com.buxiaoxia.business.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xw on 2017/3/2.
 * 2017-03-02 19:00
 */
@RestController
@RequestMapping("/api/v1.0/hello")
public class HelloController {

	@RequestMapping(method = RequestMethod.GET)
	public String getHello(){
		return "Hi~,I am Hello Service";
	}

}
