package com.itstyle.modules.web.controller;

import com.itstyle.common.util.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(tags ="支付后台")
@Controller
@RequestMapping(value = "pay")
public class PayController {

	private static final Logger logger = LoggerFactory.getLogger(PayController.class);

	@ApiOperation(value="登陆")
	@RequestMapping(value="login",method=RequestMethod.POST)
	public @ResponseBody String login(String account,String password) {
		logger.info("登陆");
		String param = "false";
		if("admin".equals(account)&&"111111".equals(password)){
			param =  "true";
		}
		return param;
	}
	@ApiOperation(value="后台展示")
	@RequestMapping(value="main",method=RequestMethod.GET)
	public String main(Model model) throws Exception {
		model.addAttribute("ip", "192.168.1.66");
		model.addAttribute("address", "青岛");
		model.addAttribute("time", DateUtils.getTime());
		return "web/main";
	}
}
