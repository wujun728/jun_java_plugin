package com.hjz.sso.server;

import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("sso")
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@RequestMapping(value="login", method={RequestMethod.GET, RequestMethod.POST})
	public String login(HttpSession session, Model model,
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="password", required=false) String password) {
		if(name == null && password == null) return "login";
		if("admin".equals(name) && "admin".equals(password)) {
			String token = UUID.randomUUID().toString();
			session.setAttribute("login", true);
			session.setAttribute("token", token);
			return "index";
		} else {
			model.addAttribute("error", true);
			model.addAttribute("message", "用户名或密码错误。");
			return "login";
		}
	}
}
