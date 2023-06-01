package com.springboot.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.springboot.model.PayModel;
import com.springboot.service.IAliService;
import com.springboot.util.AliPayUtil;
import com.springboot.util.PropKit;

/**
 * 支付宝支付
 * 
 * @author lenovo
 *
 */
@Service
public class AliServiceImpl implements IAliService {

	private static final Logger logger = Logger.getLogger(AliServiceImpl.class);

	/**
	 * 支付宝H5支付
	 */
	@Override
	public String h5Pay(PayModel payModel) {
		try {

			AlipayTradeWapPayRequest alipay_request = new AlipayTradeWapPayRequest();

			// 封装请求支付信息
			AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();
			model.setOutTradeNo(payModel.getOutTradeNno());
			model.setSubject(payModel.getBody());
			model.setTotalAmount(payModel.getAmount().toString());
			model.setBody(payModel.getBody());
			model.setProductCode("QUICK_WAP_WAY");
			alipay_request.setBizModel(model);
			// 设置异步通知地址
			alipay_request.setNotifyUrl(PropKit.get("alipay.pay.notify.url"));
			// 设置同步地址
			alipay_request.setReturnUrl(PropKit.get("alipay.pay.pc.success.url"));
			// 调用SDK生成表单
			return AliPayUtil.geAlipayClient().pageExecute(alipay_request).getBody();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 支付宝扫码支付
	 */
	@Override
	public String qrCodePay(PayModel payModel) {
		try {

			AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("out_trade_no", payModel.getOutTradeNno());// 订单号
			map.put("total_amount", payModel.getAmount().toString());// 交易金额,单位:元
			map.put("subject", payModel.getBody());// 订单标题
			request.setBizContent(JSON.toJSONString(map));
			// 设置异步通知地址
			request.setNotifyUrl(PropKit.get("alipay.pay.notify.url"));
			// 设置同步地址
			request.setReturnUrl(PropKit.get("alipay.pay.pc.success.url"));
			AlipayTradePrecreateResponse response = AliPayUtil.geAlipayClient().execute(request);
			if (!response.isSuccess()) {
				logger.info(String.format("---------------订单号为%s的扫码支付：链接生成失败！-------------------",
						payModel.getOutTradeNno()));
				return null;
			}
			JSONObject json = JSON.parseObject(response.getBody());
			JSONObject result = JSON.parseObject(json.getString("alipay_trade_precreate_response"));
			System.out.println(result);
			return result.getString("qr_code");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}

	}

	/**
	 * pc网页支付
	 */
	@Override
	public String pcPay(PayModel payModel) {
		try {

			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();// 创建API对应的request
			// 设置异步通知地址
			alipayRequest.setNotifyUrl(PropKit.get("alipay.pay.notify.url"));
			// 设置同步地址
			alipayRequest.setReturnUrl(PropKit.get("alipay.pay.pc.success.url"));

			Map<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put("out_trade_no", payModel.getOutTradeNno());
			hashMap.put("product_code", "FAST_INSTANT_TRADE_PAY");
			hashMap.put("total_amount", payModel.getAmount().toString());
			hashMap.put("subject", payModel.getBody());

			String form = "";
			try {
				form = AliPayUtil.geAlipayClient().pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
			} catch (AlipayApiException e) {
				e.printStackTrace();
			}
			return form;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

}
