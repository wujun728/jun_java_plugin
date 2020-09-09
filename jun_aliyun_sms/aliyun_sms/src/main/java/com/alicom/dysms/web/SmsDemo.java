package com.alicom.dysms.web;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.alicom.dysms.config.SmsUtil;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.google.gson.JsonObject;
/**
 * 阿里短信发送Demo
 * 创建者 科帮网
 * 创建时间	2017年6月29日
 *
 */
public class SmsDemo {
	private static final Logger LOG = LogManager.getLogger(SmsDemo.class.getName());
    public static void main(String[] args) throws ClientException, InterruptedException {
    	SendSmsRequest request = new SendSmsRequest();
    	//必填:待发送手机号
		request.setPhoneNumbers("18888888888");
		//尊敬的${name}，您正进行科帮网的身份验证，验证码${number}，打死不告诉别人！
		JsonObject params = new JsonObject();
		params.addProperty("name", "蛋蛋");
		params.addProperty("number", "521521");
		request.setTemplateParam(params.toString());
        SendSmsResponse response = SmsUtil.sendSms(request);
        LOG.info("--------短信接口返回的数据--------");
        if("OK".equals(response.getCode())){
        	System.out.println("Code=" + response.getCode());
        	System.out.println("Message=" + response.getMessage());
        	System.out.println("RequestId=" + response.getRequestId());
        	System.out.println("BizId=" + response.getBizId());
        	LOG.info("短信发送成功");
        }
    }
}
