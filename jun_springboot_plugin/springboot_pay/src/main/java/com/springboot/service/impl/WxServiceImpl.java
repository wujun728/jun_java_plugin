package com.springboot.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.springboot.model.PayModel;
import com.springboot.service.IWxService;
import com.springboot.util.BigDecimalUtil;
import com.springboot.util.HttpKit;
import com.springboot.util.PropKit;
import com.springboot.util.WxPayUtil;
import com.springboot.util.XMLUtil;

/**
 * 微信支付
 * 
 * @author lenovo
 *
 */
@Service
public class WxServiceImpl implements IWxService {

	private static final Logger logger = Logger.getLogger(WxServiceImpl.class);

	/**
	 * 微信扫码支付
	 */
	@Override
	public String wxQrCode(PayModel payModel) throws Exception {
		String currTime = WxPayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = WxPayUtil.buildRandom(4) + "";
		// 随机字符串
		String nonce_str = strTime + strRandom;
		// 交易类型
		// JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
		// MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
		String trade_type = "NATIVE";
		String time_start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DATE, 1);
		String time_expire = new SimpleDateFormat("yyyyMMddHHmmss").format(ca.getTime());
		SortedMap<Object, Object> packageParams = WxPayUtil.createWxParam(PropKit.get("weixin.pay.appid"),
				PropKit.get("weixin.pay.mchid"), nonce_str, PropKit.get("weixin.pay.notify_url"), trade_type);
		packageParams.put("body", payModel.getBody());
		packageParams.put("out_trade_no", payModel.getOutTradeNno());
		packageParams.put("total_fee", BigDecimalUtil.mul(payModel.getAmount().toString(), "100").intValue());
		packageParams.put("spbill_create_ip", payModel.getIp());
		packageParams.put("time_start", time_start);
		packageParams.put("time_expire", time_expire);
		String sign = WxPayUtil.createSign("UTF-8", packageParams, PropKit.get("weixin.pay.key"));
		packageParams.put("sign", sign);
		String requestXML = WxPayUtil.getRequestXml(packageParams);
		System.out.println("请求xml：：：：" + requestXML);
		String resXml = HttpKit.post(PropKit.get("weixin.pay.pay_url"), requestXML);
		System.out.println("返回xml：：：：" + resXml);
		Map map = XMLUtil.doXMLParse(resXml);
		String urlCode = (String) map.get("code_url");
		System.out.println("打印调用统一下单接口生成二维码url:::::" + urlCode);
		return urlCode;
	}

	/**
	 * 微信 公众号支付
	 */
	@Override
	public String wxJssdkPay(PayModel payModel) throws Exception {
		String currTime = WxPayUtil.getCurrTime();
		String strTime = currTime.substring(8, currTime.length());
		String strRandom = WxPayUtil.buildRandom(4) + "";
		// 随机字符串
		String nonce_str = strTime + strRandom;
		// 交易类型
		// JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
		// MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
		String trade_type = "JSAPI";
		String time_start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Calendar ca = Calendar.getInstance();
		ca.setTime(new Date());
		ca.add(Calendar.DATE, 1);
		String time_expire = new SimpleDateFormat("yyyyMMddHHmmss").format(ca.getTime());
		SortedMap<Object, Object> packageParams = WxPayUtil.createWxParam(PropKit.get("weixin.pay.appid"),
				PropKit.get("weixin.pay.mchid"), nonce_str, PropKit.get("weixin.pay.notify_url"), trade_type);
		packageParams.put("body", payModel.getBody());
		packageParams.put("out_trade_no", payModel.getOutTradeNno());
		packageParams.put("total_fee", BigDecimalUtil.mul(payModel.getAmount().toString(), "100").intValue());
		packageParams.put("spbill_create_ip", payModel.getIp());
		packageParams.put("time_start", time_start);
		packageParams.put("time_expire", time_expire);
		packageParams.put("openid", payModel.getOpenId());
		packageParams.put("sign_type", "MD5");
		String sign = WxPayUtil.createSign("UTF-8", packageParams, PropKit.get("weixin.pay.key"));
		packageParams.put("sign", sign);
		String requestXML = WxPayUtil.getRequestXml(packageParams);
		System.out.println("请求xml：：：：" + requestXML);
		String resXml = HttpKit.post(PropKit.get("weixin.pay.pay_url"), requestXML);
		System.out.println("返回xml：：：：" + resXml);
		return resXml;
	}

	/**
	 * 微信H5支付
	 */
	@Override
	public String h5Pay(PayModel payModel) throws Exception {
		try {
			String currTime = WxPayUtil.getCurrTime();
			String strTime = currTime.substring(8, currTime.length());
			String strRandom = WxPayUtil.buildRandom(4) + "";
			// 随机字符串
			String nonce_str = strTime + strRandom;
			// 交易类型
			// JSAPI--公众号支付、NATIVE--原生扫码支付、APP--app支付，统一下单接口trade_type的传参可参考这里
			// MICROPAY--刷卡支付，刷卡支付有单独的支付接口，不调用统一下单接口
			String trade_type = "MWEB";
			String time_start = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
			Calendar ca = Calendar.getInstance();
			ca.setTime(new Date());
			ca.add(Calendar.DATE, 1);
			String time_expire = new SimpleDateFormat("yyyyMMddHHmmss").format(ca.getTime());
			SortedMap<Object, Object> packageParams = WxPayUtil.createWxParam(PropKit.get("weixin.pay.appid"),
					PropKit.get("weixin.pay.mchid"), nonce_str, PropKit.get("weixin.pay.notify_url"), trade_type);
			packageParams.put("body", payModel.getBody());
			packageParams.put("out_trade_no", payModel.getOutTradeNno());
			packageParams.put("total_fee", BigDecimalUtil.mul(payModel.getAmount().toString(), "100").intValue());
			packageParams.put("spbill_create_ip", payModel.getIp());
			packageParams.put("time_start", time_start);
			packageParams.put("time_expire", time_expire);
			packageParams.put("sign_type", "MD5");
			String sign = WxPayUtil.createSign("UTF-8", packageParams, PropKit.get("weixin.pay.key"));
			packageParams.put("sign", sign);
			String requestXML = WxPayUtil.getRequestXml(packageParams);
			System.out.println("请求xml：：：：" + requestXML);
			String resXml = HttpKit.post(PropKit.get("weixin.pay.pay_url"), requestXML);
			System.out.println("返回xml：：：：" + resXml);
			return resXml;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

}
