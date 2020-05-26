package com.zhu.kaptcha.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zhu.kaptcha.service.UserService;
import com.zhu.kaptcha.util.CodeUtil;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping("/login")
	public String login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord,
			HttpServletRequest request) {
		boolean result = userService.login(userName, passWord);
		if (!CodeUtil.checkVerifyCode(request)) {
			request.setAttribute("codeErr", "验证码有误!");
			return "fail";
		} else {
			if (result) {
				request.setAttribute("user", userName);
				return "success";
			} else {
				request.setAttribute("errMsg", "用户名或密码错误!");
				return "fail";
			}
		}

	}

}
