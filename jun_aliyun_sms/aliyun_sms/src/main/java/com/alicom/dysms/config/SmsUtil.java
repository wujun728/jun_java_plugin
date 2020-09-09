package com.alicom.dysms.config;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
/**
 * 短信发送工具类
 * 创建者  科帮网
 * 创建时间	2017年6月29日
 *
 */
public class SmsUtil {
	private static final Logger LOG = LogManager.getLogger(SmsUtil.class.getName());
	public static SendSmsResponse sendSms(SendSmsRequest request) {
		SendSmsResponse sendSmsResponse = null;
		LOG.info("发送手机验证码:"+request.getPhoneNumbers());
		try {
			IAcsClient acsClient = AliSmsConfig.getAcsClient();
			//必填:短信签名-可在短信控制台中找到
			request.setSignName(AliSmsConfig.signName);
			//必填:短信模板-可在短信控制台中找到
			request.setTemplateCode(AliSmsConfig.templateCode);
			sendSmsResponse = acsClient.getAcsResponse(request);
		} catch (Exception e) {
			LOG.error("短信发送异常:"+request.getPhoneNumbers(), e);
		}
		return sendSmsResponse;
	}
}
