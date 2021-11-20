package com.jun.plugin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jun.plugin.model.User;

@Controller
@RequestMapping("/fm")
public class FmController {
	
	@RequestMapping("/t")
	public String toIndex(Model model){
		User user=new User();
		user.setUserName("多大的"); 
		model.addAttribute("user",user);
		return "test";
	}
	
}
