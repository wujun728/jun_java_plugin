package com.itstyle.modules.alipay.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.BufferedOutputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;
import com.itstyle.common.constants.Constants;
import com.itstyle.common.model.Product;
import com.itstyle.modules.alipay.service.IAliPayService;
import com.itstyle.modules.alipay.util.AliPayConfig;
/**
 * 支付宝支付
 * 创建者 科帮网
 * 创建时间	2017年7月30日
 */
@Api(tags ="支付宝支付")
@Controller
@RequestMapping(value = "alipay")
public class AliPayController {

	private static final Logger logger = LoggerFactory.getLogger(AliPayController.class);

	@Autowired
	private IAliPayService aliPayService;
	

	@ApiOperation(value="电脑支付")
	@RequestMapping(value="pcPay",method=RequestMethod.POST)
    public String  pcPay(Product product,ModelMap map) {
		logger.info("电脑支付");
		String form  =  aliPayService.aliPayPc(product);
		map.addAttribute("form", form);
		return "alipay/pay";
    }
	@ApiOperation(value="手机H5支付")
	@RequestMapping(value="mobilePay",method=RequestMethod.POST)
    public String  mobilePay(Product product,ModelMap map) {
		logger.info("手机H5支付");
		String form  =  aliPayService.aliPayMobile(product);
		map.addAttribute("form", form);
		return "alipay/pay";
    }
	@ApiOperation(value="二维码支付")
	@RequestMapping(value="qcPay",method=RequestMethod.POST)
    public String  qcPay(Product product,ModelMap map) {
		logger.info("二维码支付");
		String message  =  aliPayService.aliPay(product);
		if(Constants.SUCCESS.equals(message)){
			String img= "../qrcode/"+product.getOutTradeNo()+".png";
			map.addAttribute("img", img);
		}else{
			//失败
		}
		return "alipay/qcpay";
    }
	@ApiOperation(value="app支付服务端")
	@RequestMapping(value="appPay",method=RequestMethod.POST)
    public String  appPay(Product product,ModelMap map) {
		logger.info("app支付服务端");
		String orderString  =  aliPayService.appPay(product);
		map.addAttribute("orderString", orderString);
		return "alipay/pay";
    }
    /**
     * 支付宝支付后台回调(二维码、H5、网站)
     * @author Wujun
     * @param request
     * @param response
     * @throws Exception  void
     * @Date	2017年7月30日
     * 更新日志
     * 2017年7月30日  科帮网 首次创建
     */
	@ApiOperation(value="支付宝支付回调(二维码、H5、网站)")
	@RequestMapping(value="pay",method=RequestMethod.POST)
	public void alipay_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String  message = "success";
		Map<String, String> params = new HashMap<>();
		// 取出所有参数是为了验证签名
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			params.put(parameterName, request.getParameter(parameterName));
		}
		//验证签名 校验签名
		boolean signVerified = false;
		try {
			signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), AliPayConfig.CHARSET, AliPayConfig.SIGN_TYPE);
			//各位同学这里可能需要注意一下,2018/01/26 以后新建应用只支持RSA2签名方式，目前已使用RSA签名方式的应用仍然可以正常调用接口，注意下自己生成密钥的签名算法
			//signVerified = AlipaySignature.rsaCheckV1(params, Configs.getAlipayPublicKey(), "UTF-8","RSA2");
			//有些同学通过 可能使用了这个API导致验签失败，特此说明一下
			//signVerified = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "UTF-8");//正式环境
		} catch (AlipayApiException e) {
			e.printStackTrace();
			message =  "failed";
		}
		if (signVerified) {
			logger.info("支付宝验证签名成功！");
			// 若参数中的appid和填入的appid不相同，则为异常通知
			if (!Configs.getAppid().equals(params.get("app_id"))) {
				logger.info("与付款时的appid不同，此为异常通知，应忽略！");
				message =  "failed";
			}else{
				String outtradeno = params.get("out_trade_no");
				//在数据库中查找订单号对应的订单，并将其金额与数据库中的金额对比，若对不上，也为异常通知
				String status = params.get("trade_status");
				if (status.equals("WAIT_BUYER_PAY")) { // 如果状态是正在等待用户付款
					logger.info(outtradeno + "订单的状态正在等待用户付款");
				} else if (status.equals("TRADE_CLOSED")) { // 如果状态是未付款交易超时关闭，或支付完成后全额退款
					logger.info(outtradeno + "订单的状态已经关闭");
				} else if (status.equals("TRADE_SUCCESS") || status.equals("TRADE_FINISHED")) { // 如果状态是已经支付成功
					logger.info("(支付宝订单号:"+outtradeno+"付款成功)");
					//这里 根据实际业务场景 做相应的操作
				} else {
					
				}
			}
		} else { // 如果验证签名没有通过
			message =  "failed";
			logger.info("验证签名失败！");
		}
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		out.write(message.getBytes());
		out.flush();
		out.close();
	}
	
	/**
	 * 支付宝支付PC端前台回调
	 * @author Wujun
	 * @param request
	 * @return  String
	 * @Date	2018年11月20日
	 * 更新日志
	 * 2018年11月20日  科帮网 首次创建
	 */
	@RequestMapping(value="/frontRcvResponse",method=RequestMethod.POST)
	public String  frontRcvResponse(HttpServletRequest request){
		try {
			//获取支付宝GET过来反馈信息
			Map<String,String> params = new HashMap<>();
			Map<String,String[]> requestParams = request.getParameterMap();
			for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
				String name = (String) iter.next();
				String[] values = (String[]) requestParams.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				//乱码解决，这段代码在出现乱码时使用
				valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
				params.put(name, valueStr);
			}
			//商户订单号
			String orderNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//前台回调验证签名 v1 or v2
			boolean signVerified = aliPayService.rsaCheckV1(params);
			if(signVerified) {
				logger.info("订单号"+orderNo+"验证签名结果[成功].");
				//处理业务逻辑
			}else {
				logger.info("订单号"+orderNo+"验证签名结果[失败].");
			}
		} catch (Exception e) {
			e.printStackTrace();
			//处理异常信息
		}
		//支付成功、跳转到成功页面
		return "success.html";
	}
}
