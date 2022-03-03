package com.baomidou.springwind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baomidou.kisso.annotation.Permission;

/**
 * <p>
 * 邮件发送接收
 * </p>
 * 
 * @author hubin
 * @Date 2016-04-13
 */
@Controller
@RequestMapping("/sys/mail")
public class MailController extends BaseController {

	/**
	 * 发送
	 */
	@Permission("1001")
	@RequestMapping("/send")
	public String send(Model model, String email) {
		if (isPost()) {
			model.addAttribute("email", email);
			model.addAttribute("loginName", getSSOToken().getData());
			boolean rlt = mailHelper.sendMail(email, "SpringWind 测试邮件！", "/mail/tplSend.html", model);
			String tipMsg = "发送邮件至【" + email + "】失败！！";
			if(rlt){
				tipMsg = "已成功发送邮件至【" + email + "】注意查收！！";
			}
			model.addAttribute("tipMsg", tipMsg);
		}
		return "/mail/send";
	}

}
