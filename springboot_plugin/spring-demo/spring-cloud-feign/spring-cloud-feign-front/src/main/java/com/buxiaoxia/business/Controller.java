package com.buxiaoxia.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by xw on 2017/3/2.
 * 2017-03-02 19:06
 */
@RestController
@RequestMapping("/api/v1.0/controller")
public class Controller {

	@Autowired
	private HelloClient helloClient;

	@RequestMapping(method = RequestMethod.GET)
	public String getStr(){
		return helloClient.getHello();
	}
}

@Component
@FeignClient(value = "spring-cloud-feign-back")
interface HelloClient{

	@RequestMapping(value = "/api/v1.0/hello",method = RequestMethod.GET)
	String getHello();

}
