package com.springboot.util;

import java.util.HashMap;
import java.util.Map;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;

/**
 * 支付宝支付工具类
 * 
 * @author lenovo
 *
 */
public class AliPayUtil {

	public static final String SUCCESS = "success";
	public static final String FAIL = "fail";

	/**
	 * 获取支付宝的签名
	 * 
	 * @param str
	 * @return
	 */
	public static String alipaySign(String str) {
		String sign;
		try {
			sign = AlipaySignature.rsaSign(str, PropKit.get("alipay.pay.private.key"),
					PropKit.get("alipay.pay.charset"), PropKit.get("alipay.pay.sign.type"));
			return sign;
		} catch (AlipayApiException e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 组装公共参数
	 * 
	 * @param method
	 *            接口名称
	 * @param returnUrl
	 *            同步通知地址（支付成功返回的地址-给用户看的）
	 * @param bizContent
	 *            业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档
	 * @return
	 */
	public static Map<String, Object> createAliParam(String method, String returnUrl, String bizContent) {
		Map<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("app_id", PropKit.get("alipay.pay.appid"));
		hashMap.put("method", method);
		hashMap.put("format", PropKit.get("alipay.pay.format"));
		hashMap.put("charset", PropKit.get("alipay.pay.charset"));
		hashMap.put("sign_type", PropKit.get("alipay.pay.sign.type"));
		hashMap.put("version", "1.0");
		hashMap.put("timestamp", DateUtil.getTime());
		hashMap.put("notify_url", PropKit.get("alipay.pay.notify.url"));
		hashMap.put("biz_content", bizContent);
		hashMap.put("return_url", returnUrl);
		return null;
	}

	/**
	 * 初始化支付宝配置
	 * 
	 * @return
	 */
	public static AlipayClient geAlipayClient() {
		AlipayClient alipayClient = new DefaultAlipayClient(PropKit.get("alipay.pay.gateway"),
				PropKit.get("alipay.pay.appid"), PropKit.get("alipay.pay.private.key"),
				PropKit.get("alipay.pay.format"), PropKit.get("alipay.pay.charset"),
				PropKit.get("alipay.pay.public.key"), PropKit.get("alipay.pay.sign.type"));
		return alipayClient;
	}
}
