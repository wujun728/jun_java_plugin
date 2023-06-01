package com.springboot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alipay.api.internal.util.AlipaySignature;
import com.springboot.util.AliPayUtil;
import com.springboot.util.PropKit;
import com.springboot.util.WxPayUtil;
import com.springboot.util.XMLUtil;

/**
 * 支付异步通知
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "/pay/notify")
public class NotifyController {

	private static final Logger logger = Logger.getLogger(NotifyController.class);

	/**
	 * 微信通知地址
	 * 
	 * @throws IOException
	 */
	@RequestMapping(value = "/wxNotify", produces = "application/xml")
	@ResponseBody
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String wxNotify(HttpServletRequest request) throws Exception {
		// 读取参数
		InputStream inputStream = request.getInputStream();
		StringBuffer sb = new StringBuffer();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		// 解析xml成map
		Map<String, String> m = new HashMap<String, String>();
		m = XMLUtil.doXMLParse(sb.toString());
		// 过滤空 设置 TreeMa
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = m.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = m.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
		// 账号信息
		String key = PropKit.get("weixin.pay.key"); // key
		// 判断签名是否正确

		if (WxPayUtil.isTenpaySign("UTF-8", packageParams, key)) {
			logger.info("微信支付成功回调");
			String resXml = "";
			if ("SUCCESS".equals(packageParams.get("result_code"))) {
				// 这里是支付成功
				String orderNo = (String) packageParams.get("out_trade_no");
				String totalFee = (String) packageParams.get("total_fee");
				logger.info(String.format("微信订单号{%s}付款成功", orderNo));
				System.out.println(String.format("微信订单号{%s}付款成功", orderNo) + "金额是：" + totalFee + "分");
				// 这里 根据实际业务场景 做相应的操作
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
						+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			} else {
				logger.info(String.format("支付失败,错误信息：{%s}", packageParams.get("err_code")));
				System.out.println(String.format("支付失败,错误信息：{%s}", packageParams.get("err_code")));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
						+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			// 返回xml文档
			return resXml;
		} else {
			logger.info("通知签名验证失败");
		}
		return null;
	}

	/**
	 * 支付宝异步通知 支付宝 支付结果异步通知2 (新的RSA2的方式)
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/aliNotify", produces = "text/plain")
	@ResponseBody
	public String aliNotify(HttpServletRequest request) throws Exception {
		Map<String, String[]> requestParams = request.getParameterMap();
		Map<String, String> params = new HashMap<String, String>();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String key = iter.next();
			String[] values = requestParams.get(key);
			if (null != values && values.length > 0) {
				params.put(key, values[0]);
			}
		}
		System.out.println("支付宝异步通知 trade_notify2 原始参数字典：" + JSON.toJSONString(params));
		if (null == params || params.size() <= 0) {
			return AliPayUtil.FAIL;
		}

		// 排序后的map
		Map<String, String> smap = new TreeMap<String, String>(params);
		smap.remove("sign_type");

		// 验证签名 -- 此方法不会去掉sign_type验签
		boolean signVerified = AlipaySignature.rsaCheckV1(smap, PropKit.get("alipay.pay.public.key"),
				PropKit.get("alipay.pay.charset"), PropKit.get("alipay.pay.sign.type"));
		System.out.println("验证签名状态：" + signVerified);
		if (signVerified) {

			// 验签成功后 要做的事情
			// 1、商户需要验证该通知数据中的out_trade_no是否为商户系统中创建的订单号，
			// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额），
			// 3、校验通知中的seller_id（或者seller_email)
			// 是否为out_trade_no这笔单据的对应的操作方（有的时候，一个商户可能有多个seller_id/seller_email），
			// 4、验证app_id是否为该商户本身。
			// 上述1、2、3、4有任何一个验证不通过，则表明本次通知是异常通知，务必忽略。在上述验证通过后商户必须根据支付宝不同类型的业务通知，正确的进行不同的业务处理，并且过滤重复的通知结果数据。在支付宝的业务通知中，只有交易通知状态为TRADE_SUCCESS或TRADE_FINISHED时，支付宝才会认定为买家付款成功。
			String trade_status = smap.get("trade_status");
			if (StringUtils.isBlank(trade_status)) {
				return AliPayUtil.FAIL;
			}

			String[] arrSuccessStat = new String[] { "TRADE_SUCCESS", "TRADE_FINISHED" };
			if (!ArrayUtils.contains(arrSuccessStat, trade_status)) {
				return AliPayUtil.FAIL;
			}

			String out_trade_no = smap.get("out_trade_no");
			if (StringUtils.isBlank(out_trade_no)) {
				return AliPayUtil.FAIL;
			}

			String total_amount = smap.get("total_amount");
			if (StringUtils.isBlank(total_amount)) {
				return AliPayUtil.FAIL;
			}

			String seller_id = smap.get("seller_id");
			if (StringUtils.isBlank(seller_id)) {
				return AliPayUtil.FAIL;
			}
			System.out.println("验签成功--订单号：" + out_trade_no + "\t支付金额：" + total_amount + "元");
			return AliPayUtil.SUCCESS;
		} else {
			return AliPayUtil.FAIL;
		}

	}
}
