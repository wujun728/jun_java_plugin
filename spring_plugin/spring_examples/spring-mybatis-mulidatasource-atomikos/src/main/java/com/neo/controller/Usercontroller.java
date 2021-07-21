package com.neo.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.neo.service.UserService;

@Controller
@RequestMapping("/user")
public class Usercontroller {
    @Resource UserService userService;
    
    @RequestMapping("/update")
	public @ResponseBody Object update(){
	     userService.updateUserinfo();
	     return "ok";
	}
    
    
          

}

