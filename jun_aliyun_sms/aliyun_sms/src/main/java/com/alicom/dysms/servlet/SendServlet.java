package com.alicom.dysms.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alicom.dysms.config.SmsUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.google.gson.JsonObject;
/**
 * 短信发送
 * 创建者 科帮网
 * 创建时间	2017年6月29日
 */
public class SendServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String mobile = request.getParameter("mobile");//手机
		String number = request.getParameter("number");//验证码
		SendSmsRequest sms = new SendSmsRequest();
		sms.setPhoneNumbers(mobile);
		JsonObject params = new JsonObject();
		params.addProperty("name", "小柒");
		params.addProperty("number", number);
		sms.setTemplateParam(params.toString());
        SendSmsResponse res = SmsUtil.sendSms(sms);
        PrintWriter out = response.getWriter();
        if("OK".equals(res.getCode())){
        	out.print("success");
        }else{
        	out.print("fail");
        }
	}
}