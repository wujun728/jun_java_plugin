package com.itstyle.modules.weixinpay.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.BufferedOutputStream;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.itstyle.common.model.Product;
import com.itstyle.common.util.AddressUtils;
import com.itstyle.common.util.DateUtils;
import com.itstyle.modules.weixinpay.service.IWeixinPayService;
import com.itstyle.modules.weixinpay.util.ConfigUtil;
import com.itstyle.modules.weixinpay.util.HttpUtil;
import com.itstyle.modules.weixinpay.util.PayCommonUtil;
import com.itstyle.modules.weixinpay.util.XMLUtil;
import com.itstyle.modules.weixinpay.util.mobile.MobileUtil;
/**
 * 微信H5支付
 * 创建者 科帮网
 * 创建时间	2017年7月31日
 *
 */
@Api(tags ="微信H5支付")
@Controller
@RequestMapping(value = "weixinMobile")
public class WeixinMobilePayController {

	private static final Logger logger = LoggerFactory.getLogger(WeixinMobilePayController.class);

	@Autowired
	private IWeixinPayService weixinPayService;
	@Value("${server.context.url}")
	private String server_url;
	
	@ApiOperation(value="H5支付(需要公众号内支付)")
	@RequestMapping(value="pay",method=RequestMethod.POST)
    public String  pay(Product product,ModelMap map) {
		logger.info("H5支付(需要公众号内支付)");
		String url =  weixinPayService.weixinPayMobile(product);
		return "redirect:"+url;
    }
	@ApiOperation(value="公众号H5支付主页")
	@RequestMapping(value="payPage",method=RequestMethod.GET)
	public String pay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//这里因为无法测试、模板下是个JSP页面、无法正常运行，请自行修改逻辑
		return "weixinpay/payPage";
	}
	@ApiOperation(value="纯H5支付(不建议在APP端使用)")
	@RequestMapping(value="h5pay",method=RequestMethod.POST)
    public String  h5pay(Product product,ModelMap map) {
		logger.info("纯H5支付(不建议在APP端使用)");
		//mweb_url为拉起微信支付收银台的中间页面，可通过访问该url来拉起微信客户端，完成支付,mweb_url的有效期为5分钟。
		String mweb_url =  weixinPayService.weixinPayH5(product);
		if(StringUtils.isNotBlank(mweb_url)){
			return "redirect:"+mweb_url;
		}else{
			return "redirect:https://blog.52itstyle.com";//自定义错误页面
		}
    }
	@ApiOperation(value="小程序支付(需要HTTPS)")
	@RequestMapping(value="smallRoutine",method=RequestMethod.POST)
    public String  smallRoutine(Product product,ModelMap map) {
		logger.info("小程序支付(需要HTTPS)、不需要支付目录和授权域名");
		String url =  weixinPayService.weixinPayMobile(product);
		return "redirect:"+url;
    }
	/**
	 * 预下单(对于已经产生的订单)
	 * @author Wujun
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception  String
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	@SuppressWarnings("rawtypes")
	@ApiOperation(value="预下单")
	@RequestMapping(value="dopay",method=RequestMethod.POST)
	public String dopay(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String orderNo = request.getParameter("outTradeNo");
		String totalFee = request.getParameter("totalFee");
		//获取code 这个在微信支付调用时会自动加上这个参数 无须设置
		String code = request.getParameter("code");
		//获取用户openID(JSAPI支付必须传openid)
		String openId = MobileUtil.getOpenId(code);
		String notify_url =server_url+"/weixinMobile/WXPayBack";//回调接口
		String trade_type = "JSAPI";// 交易类型H5支付 也可以是小程序支付参数
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		ConfigUtil.commonParams(packageParams);
		packageParams.put("body","报告");// 商品描述
		packageParams.put("out_trade_no", orderNo);// 商户订单号
		packageParams.put("total_fee", totalFee);// 总金额
		packageParams.put("spbill_create_ip", AddressUtils.getIpAddr(request));// 发起人IP地址
		packageParams.put("notify_url", notify_url);// 回调地址
		packageParams.put("trade_type", trade_type);// 交易类型
		packageParams.put("openid", openId);//用户openID
		String sign = PayCommonUtil.createSign("UTF-8", packageParams,ConfigUtil.API_KEY);
		packageParams.put("sign", sign);// 签名
		String requestXML = PayCommonUtil.getRequestXml(packageParams);
		String resXml = HttpUtil.postData(ConfigUtil.UNIFIED_ORDER_URL, requestXML);
		Map map = XMLUtil.doXMLParse(resXml);
		String returnCode = (String) map.get("return_code");
		String returnMsg = (String) map.get("return_msg");
		StringBuffer url = new StringBuffer();
		if("SUCCESS".equals(returnCode)){
			String resultCode = (String) map.get("result_code");
			String errCodeDes = (String) map.get("err_code_des");
			if("SUCCESS".equals(resultCode)){
				//获取预支付交易会话标识
				String prepay_id = (String) map.get("prepay_id");
				String prepay_id2 = "prepay_id=" + prepay_id;
				String packages = prepay_id2;
				SortedMap<Object, Object> finalpackage = new TreeMap<>();
				String timestamp = DateUtils.getTimestamp();
				String nonceStr = packageParams.get("nonce_str").toString();
				finalpackage.put("appId",  ConfigUtil.APP_ID);
				finalpackage.put("timeStamp", timestamp);
				finalpackage.put("nonceStr", nonceStr);
				finalpackage.put("package", packages);  
				finalpackage.put("signType", "MD5");
				//这里很重要  参数一定要正确 狗日的腾讯 参数到这里就成大写了
				//可能报错信息(支付验证签名失败 get_brand_wcpay_request:fail)
				sign = PayCommonUtil.createSign("UTF-8", finalpackage,ConfigUtil.API_KEY);
				url.append("redirect:/weixinMobile/payPage?");
				url.append("timeStamp="+timestamp+"&nonceStr=" + nonceStr + "&package=" + packages);
				url.append("&signType=MD5" + "&paySign=" + sign+"&appid="+ ConfigUtil.APP_ID);
				url.append("&orderNo="+orderNo+"&totalFee="+totalFee);
			}else{
				logger.info("订单号:{}错误信息:{}",orderNo,errCodeDes);
				url.append("redirect:/weixinMobile/error?code=0&orderNo="+orderNo);//该订单已支付
			}
		}else{
			logger.info("订单号:{}错误信息:{}",orderNo,returnMsg);
			url.append("redirect:/weixinMobile/error?code=1&orderNo="+orderNo);//系统错误
		}
		return url.toString();
	}
	/**
	 * 手机支付完成回调
	 * @author Wujun
	 * @param request
	 * @param response
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	@ApiOperation(value="手机支付完成回调")
	@RequestMapping(value="WXPayBack",method=RequestMethod.POST)
	public void WXPayBack(HttpServletRequest request, HttpServletResponse response){
		String resXml = "";
		try {
			//解析XML
			Map<String, String> map = MobileUtil.parseXml(request);
	        String return_code = map.get("return_code");//状态
	        String out_trade_no = map.get("out_trade_no");//订单号
			if (return_code.equals("SUCCESS")) {
				if (out_trade_no != null) {
					//处理订单逻辑
					logger.info("微信手机支付回调成功订单号:{}",out_trade_no);
					resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
				}
			}else{
				logger.info("微信手机支付回调失败订单号:{}",out_trade_no);
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
		} catch (Exception e) {
			logger.error("手机支付回调通知失败",e);
			 resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		try {
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
