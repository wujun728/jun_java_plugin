package com.chentongwei.controller.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chentongwei.util.GeetestConstant;
import com.chentongwei.util.GeetestLib;

/**
 * 使用Get的方式返回challenge和capthca_id,此方式以实现前后端完全分离的开发模式
 * 
 * @author TongWei.Chen 2017-6-4 11:37:032
 */
@RestController
@RequestMapping("/common/captcha")
public class CaptchaController {

	private static final Logger LOG = LoggerFactory.getLogger(CaptchaController.class);

	@Autowired
	private GeetestConstant geetestConstant;
	
	/**
	 * 获取验证码
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/getCaptcha", method = RequestMethod.GET)
	public void getCaptcha(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		GeetestLib gtSdk = new GeetestLib(geetestConstant.getId(), geetestConstant.getKey(), 
				geetestConstant.getNewfailback());

		String resStr = "{}";
		
		//自定义userid
		String userid = "doutu";

		//进行验证预处理
		int gtServerStatus = gtSdk.preProcess(userid);
		
		//将服务器状态设置到session中
		request.getSession().setAttribute(gtSdk.gtServerStatusSessionKey, gtServerStatus);
		//将userid设置到session中
		request.getSession().setAttribute("userid", userid);
		
		resStr = gtSdk.getResponseStr();
		LOG.info("获取验证码成功");
		PrintWriter out = response.getWriter();
		out.println(resStr);

	}
}
