package com.itstyle.modules.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itstyle.common.utils.DateUtil;

@Controller
@RequestMapping(value = "pay")
public class PayController {
	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@RequestMapping("/index")
    public String   index() {
		logger.info("登陆首页");
        return "web/index";
    }
	@RequestMapping(value = "login")
	@ResponseBody
	public String login(HttpServletRequest request, HttpServletResponse response,
			           String account,String password) throws Exception {
		logger.info("登陆");
		String param = "false";
		if("admin".equals(account)&&"111111".equals(password)){
			param =  "true";
		}
		return param;
	}
	@RequestMapping(value = "main")
	public String main(HttpServletRequest request, HttpServletResponse response,Model model) throws Exception {
		model.addAttribute("ip", "192.168.1.66");
		model.addAttribute("address", "青岛");
		model.addAttribute("time", DateUtil.getTime());
		return "web/main";
	}
}
