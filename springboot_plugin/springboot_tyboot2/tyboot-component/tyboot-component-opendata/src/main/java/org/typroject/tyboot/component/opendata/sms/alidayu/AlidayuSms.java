package org.typroject.tyboot.component.opendata.sms.alidayu;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.BeanCreationException;
import org.typroject.tyboot.core.foundation.utils.StringUtil;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;

import java.util.List;
import java.util.Map;

/** 
 * 
 * <pre>
 *  Tyrest
 *  File: AlidayuSms.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: AlidayuSms.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月17日		magintrursh		Initial.
 *
 * </pre>
 */
public class AlidayuSms  {


	private String alidayuAppkey;
	
	private String alidayuAppSecret;
	
	private String alidayuSmsSignName;


	/**
	 *
	 * @param alidayuAppkey
	 * @param alidayuAppSecret
	 * @param alidayuSmsSignName
	 */
	public  AlidayuSms(String alidayuAppkey,String alidayuAppSecret,String alidayuSmsSignName)
	{
		this.alidayuAppkey 		= alidayuAppkey;
		this.alidayuAppSecret   = alidayuAppSecret;
		this.alidayuSmsSignName = alidayuSmsSignName;

	}




	//产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	//产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	public void afterPropertiesSet() throws Exception {
		if( ValidationUtil.isEmpty(alidayuAppkey)
				|| ValidationUtil.isEmpty(alidayuAppSecret)
				|| ValidationUtil.isEmpty(alidayuSmsSignName))
		{			
			throw new BeanCreationException("阿里大鱼短信配置校验失败!");
		}
		
	}
	
	public SendSmsResponse sendSms(String templateCode, Map<String, String> params, String mobile) throws Exception {

		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", alidayuAppkey, alidayuAppSecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号
		request.setPhoneNumbers(mobile);
		//必填:短信签名-可在短信控制台中找到
		request.setSignName(alidayuSmsSignName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(JSONObject.fromObject(params).toString());

		request.setOutId("yourOutId");

		//hint 此处可能会抛出异常，注意catch
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

		return sendSmsResponse;
	};

	public SendSmsResponse sendSms(String templateCode, Map<String, String> params, List<String> mobiles) throws Exception {
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", alidayuAppkey, alidayuAppSecret);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);

		//组装请求对象-具体描述见控制台-文档部分内容
		SendSmsRequest request = new SendSmsRequest();
		//必填:待发送手机号

		//必填:短信签名-可在短信控制台中找到
		request.setSignName(alidayuSmsSignName);
		//必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		//可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		request.setTemplateParam(JSONObject.fromObject(params).toString());


		//群发短信需传入多个号码，以英文逗号分隔，一次调用最多传入200个号码

		String  phoneNumbers = "";
		for(String phoneNumber:mobiles)
		{
			phoneNumbers += phoneNumber+",";
		}
		if(!ValidationUtil.isEmpty(mobiles))
		{
			phoneNumbers.substring(0,phoneNumbers.length()-1);
		}

		//设置群发短信电话号码
		request.setPhoneNumbers(phoneNumbers);

		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		return sendSmsResponse;
	};

}

/*
*$Log: av-env.bat,v $
*/