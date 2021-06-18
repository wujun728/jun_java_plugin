/*******************************************************************************
 * Copyright (c) 2005, 2014 springside.github.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *******************************************************************************/
package com.jd.ssh.sshdemo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jd.ssh.sshdemo.entity.Admin;
import com.jd.ssh.sshdemo.service.AdminService;

/**
 * 用户注册的Controller.
 * 
 * @author Wujun
 */
@Controller
@RequestMapping(value = "/register")
public class RegisterController {

	@Autowired
	private AdminService adminService;

	@RequestMapping(method = RequestMethod.GET)
	public String registerForm() {
		return "account/register";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String register(Admin user, RedirectAttributes redirectAttributes) {
		
		adminService.entryptPassword(user);
		user.setRoles("user");
		
		user.setIsAccountEnabled(true);
    	user.setIsAccountExpired(false);
    	user.setIsCredentialsExpired(false);
    	user.setIsAccountLocked(false);
    	
		adminService.save(user);
		redirectAttributes.addFlashAttribute("username", user.getUsername());
		return "redirect:/login";
	}

	/**
	 * Ajax请求校验loginName是否唯一。
	 */
	@RequestMapping(value = "checkLoginName")
	@ResponseBody
	public String checkLoginName(@RequestParam("loginName") String loginName) {
		if (adminService.getAdminByUsername(loginName) == null) {
			return "true";
		} else {
			return "false";
		}
	}
}
