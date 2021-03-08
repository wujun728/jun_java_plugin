package com.itstyle.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller(value="errorpage")
public class ErrorController {
	@RequestMapping("/404")
	public String error400(){
		return "error/404";
	}

	@RequestMapping("/500")
	public String error500()  {
		return "error/500";
	}
}
