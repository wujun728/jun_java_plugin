package com.itstyle.test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.demo.trade.config.Configs;
import com.itstyle.modules.alipay.util.AliPayConfig;
/**
 * 测试APP服务端逻辑
 * 创建者 科帮网
 * 创建时间	2017年9月21日
 *
 */
public class AppAliPay{
	public static void main(String[] args) {
		Configs.init("E:\\zfbinfo.properties");
		// 实例化客户端
		AlipayClient alipayClient = AliPayConfig.getAlipayClient();
		// 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		// SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody("APP支付");
		model.setSubject("APP支付");
		model.setOutTradeNo("20179089");
		model.setTimeoutExpress("30m");
		model.setTotalAmount("100");
		model.setProductCode("QUICK_MSECURITY_PAY");
		request.setBizModel(model);
		request.setNotifyUrl("https://blog.52itstyle.com");
		try {
			// 这里和普通的接口调用不同，使用的是sdkExecute
			AlipayTradeAppPayResponse response = alipayClient
					.sdkExecute(request);
			System.out.println(response.getBody());// 就是orderString
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}
}
