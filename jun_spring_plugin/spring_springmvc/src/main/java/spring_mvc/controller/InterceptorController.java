package spring_mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class InterceptorController {

	@RequestMapping("/interceptor")
	public String interceptor() {
		
		System.out.println("InterceptorController-->interceptorss");
		
		return "interceptor";
	}
	
	@RequestMapping("/interceptor/2")
	public String interceptor2() {
		
		System.out.println("InterceptorController-->interceptor2");
		
		return "interceptor";
	}

}
