package com.springboot.controller;

import java.math.BigDecimal;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.PayModel;
import com.springboot.service.IWxService;
import com.springboot.util.IPKit;
import com.springboot.util.PropKit;
import com.springboot.util.WxPayUtil;
import com.springboot.util.WxUtil;
import com.springboot.util.XMLUtil;

/**
 * 微信支付
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "/wx/pay")
public class WxController {

	@Autowired
	private IWxService wxService;

	@RequestMapping(value = "/jsPay")
	public ModelAndView wx(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView();
		String prepay_id = request.getParameter("prepay_id");
		Map<String, Object> hashMap = WxUtil.cresateSignature();
		mv.addObject("hashMap", hashMap);
		// 拿到了prepay_id，再次生成签名返回给页面。paySign
		SortedMap<Object, Object> paySignParameters = new TreeMap<Object, Object>();
		paySignParameters.put("appId", PropKit.get("weixin.pay.appid"));
		paySignParameters.put("nonceStr", hashMap.get("noncestr"));
		paySignParameters.put("package", "prepay_id=" + prepay_id);
		paySignParameters.put("signType", "MD5");
		paySignParameters.put("timeStamp", hashMap.get("timestamp"));
		String sign = WxPayUtil.createSign("utf-8", paySignParameters, PropKit.get("weixin.pay.key"));
		mv.addObject("sign", sign);
		mv.addObject("prepay_id", prepay_id);
		mv.addObject("appId", PropKit.get("weixin.pay.appid"));
		mv.setViewName("wx/wx");
		return mv;
	}

	/**
	 * @throws Exception
	 * 
	 */
	@RequestMapping(value = "/jsSdkPay")
	public String jsSdkPay(HttpServletRequest request) throws Exception {
		PayModel model = new PayModel();
		String openId = request.getParameter("openId");
		model.setAmount(new BigDecimal("0.01"));
		model.setBody("定制合同：劳动合同通用版");
		model.setIp(IPKit.getIpAddr(request));
		model.setOutTradeNno(com.springboot.util.IdKit.getDateId("1"));
		model.setOpenId(openId);
		Map map = XMLUtil.doXMLParse(wxService.wxJssdkPay(model));
		System.out.println(map);
		return "redirect:http://test.jiedanba.cn/wx/pay/jsPay?prepay_id=" + map.get("prepay_id");
	}

	/**
	 * 获取用户openid
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOpenId")
	public String getOpenId(HttpServletRequest request) {
		String code = request.getParameter("code");
		Map<String, Object> resultMap = WxUtil.getUserOpenId(PropKit.get("weixin.pay.appid"),
				PropKit.get("weixin.pay.appsecret"), code);
		return "redirect:http://test.jiedanba.cn/wx/pay/jsSdkPay?openId=" + resultMap.get("openid");
	}

	/**
	 * 微信扫码支付
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/qrCode")
	public ModelAndView qrCode(HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		PayModel model = new PayModel();
		String openId = request.getParameter("openId");
		model.setAmount(new BigDecimal("0.01"));
		model.setBody("定制合同：劳动合同通用版");
		model.setIp(IPKit.getIpAddr(request));
		model.setOutTradeNno(com.springboot.util.IdKit.getDateId("1"));
		model.setOpenId(openId);
		String url = wxService.wxQrCode(model);
		mv.addObject("url", url);
		mv.setViewName("wx/qrCode");
		return mv;
	}

	@RequestMapping(value = "/h5Pay", produces = "application/xml")
	@ResponseBody
	public String h5Pay(HttpServletRequest request) throws Exception {
		PayModel model = new PayModel();
		String openId = request.getParameter("openId");
		model.setAmount(new BigDecimal("0.01"));
		model.setBody("定制合同：劳动合同通用版");
		model.setIp(IPKit.getIpAddr(request));
		model.setOutTradeNno(com.springboot.util.IdKit.getDateId("1"));
		model.setOpenId(openId);
		return wxService.h5Pay(model);
	}

}
