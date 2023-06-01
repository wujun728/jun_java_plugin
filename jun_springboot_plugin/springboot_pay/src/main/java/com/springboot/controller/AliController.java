package com.springboot.controller;

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.model.PayModel;
import com.springboot.service.IAliService;
import com.springboot.util.IPKit;
import com.springboot.util.PropKit;

/**
 * 支付宝支付
 * 
 * @author lenovo
 *
 */
@Controller
@RequestMapping(value = "/ali/pay")
public class AliController {
	@Autowired
	private IAliService aliService;

	/**
	 * H5网页支付
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/createH5Pay")
	public void createH5Pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PayModel model = new PayModel();
		model.setAmount(new BigDecimal("0.01"));
		model.setBody("定制合同：劳动合同通用版");
		model.setIp(IPKit.getIpAddr(request));
		String orderId = com.springboot.util.IdKit.getDateId("1");
		System.out.println("订单编号：" + orderId);
		model.setOutTradeNno(orderId);
		String form = aliService.h5Pay(model);
		System.out.println(form);
		response.setContentType("text/html;charset=" + PropKit.get("alipay.pay.charset"));
		response.getWriter().write(form);// 直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}

	/**
	 * 扫码支付
	 * 
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/qrCodePay")
	public ModelAndView qrCodePay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView();
		PayModel model = new PayModel();
		model.setAmount(new BigDecimal("0.01"));
		model.setBody("定制合同：劳动合同通用版");
		model.setIp(IPKit.getIpAddr(request));
		String orderId = com.springboot.util.IdKit.getDateId("1");
		System.out.println("订单编号：" + orderId);
		model.setOutTradeNno(orderId);
		String url = aliService.qrCodePay(model);
		System.out.println("支付链接：" + url);
		mv.addObject("url", url);
		mv.setViewName("ali/qrCode");
		return mv;
	}

	@RequestMapping(value = "/pcPay")
	public void pcPay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		PayModel model = new PayModel();
		model.setAmount(new BigDecimal("0.01"));
		model.setBody("定制合同：劳动合同通用版");
		model.setIp(IPKit.getIpAddr(request));
		String orderId = com.springboot.util.IdKit.getDateId("1");
		System.out.println("订单编号：" + orderId);
		model.setOutTradeNno(orderId);
		String form = aliService.pcPay(model);
		System.out.println(form);
		response.setContentType("text/html;charset=" + PropKit.get("alipay.pay.charset"));
		response.getWriter().write(form);// 直接将完整的表单html输出到页面
		response.getWriter().flush();
		response.getWriter().close();
	}

}
