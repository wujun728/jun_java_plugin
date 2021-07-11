package com.kenfo.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.Configuration;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.IdentityService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.identity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserController {

		//流程引擎
		ProcessEngine processEngine = Configuration.getProcessEngine();
		IdentityService identityService = processEngine.getIdentityService();
		
	@RequestMapping(value="/login",method=RequestMethod.GET)
	public String deploy(Map<String,Object> model){
		
		return "login";
	}
	
	@RequestMapping(value="/doLogin",method=RequestMethod.POST)
	public String doLogin(String userName,String email,String type,HttpSession session,Model model){
		if(StringUtils.isEmpty(userName)){
			model.addAttribute("message","用户名不能为空!");
			return "login";
		}
		if(StringUtils.isNotEmpty(type)){
			session.setAttribute("userName", userName);
			if(StringUtils.isEmpty(email)){
				email = "xxg3053@qq.com";
			}
			User u = identityService.findUserById(userName);
			if(u == null){
				identityService.createUser(userName, userName, "kenfo", email);
			}
			if(type.equals("leave")){
				return "redirect:/leave/index";
			}else if(type.equals("sign")){
				return "redirect:/sign/index";
			}else if(type.equals("mail")){
				return "redirect:/mail/index";
			}else{
				return "index";
			}
		}else{
			return "index";
		}
		
	}
	
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public String doLogin(HttpSession session,Model model){
		session.removeAttribute("userName");
		return "login";
	}
}
