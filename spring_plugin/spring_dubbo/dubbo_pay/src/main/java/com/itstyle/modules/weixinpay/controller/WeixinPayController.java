package com.itstyle.modules.weixinpay.controller;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itstyle.common.constants.Constants;
import com.itstyle.common.model.Product;
import com.itstyle.modules.weixinpay.service.IWeixinPayService;
import com.itstyle.modules.weixinpay.util.ConfigUtil;
import com.itstyle.modules.weixinpay.util.HttpUtil;
import com.itstyle.modules.weixinpay.util.PayCommonUtil;
import com.itstyle.modules.weixinpay.util.XMLUtil;

/**
 * 微信二维码支付
 * 创建者 科帮网
 * 创建时间	2017年7月31日
 */
@Controller
@RequestMapping(value = "weixin")
public class WeixinPayController {
	private static final Logger logger = LoggerFactory.getLogger(WeixinPayController.class);
	@Autowired
	private IWeixinPayService weixinPayService;
	
	@Value("${wexinpay.notify.url}")
	private String notify_url;
	
	@RequestMapping("/index")
    public String   index() {
        return "weixinpay/index";
    }
	@RequestMapping("/qcPay")
    public String  qcPay(Product product,ModelMap map) {
		logger.info("二维码支付");
		//参数自定义  这只是个Demo
		product.setProductId("20170721");
		product.setBody("两个苹果八毛钱 ");
		product.setSpbillCreateIp("192.168.1.66");
		String message  =  weixinPayService.weixinPay(product);
		if(Constants.SUCCESS.equals(message)){
			String img= "../qrcode/"+product.getOutTradeNo()+".png";
			map.addAttribute("img", img);
		}else{
			//失败
		}
		return "weixinpay/qcpay";
    }
	/**
	 * 支付后台回调
	 * @Author  科帮网
	 * @param request
	 * @param response
	 * @throws Exception  void
	 * @Date	2017年7月31日
	 * 更新日志
	 * 2017年7月31日  科帮网 首次创建
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "pay")
	public void weixin_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
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

		// 过滤空 设置 TreeMap
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
		String key = ConfigUtil.API_KEY; // key
		// 判断签名是否正确
		if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, key)) {
			logger.info("微信支付成功回调");
			// ------------------------------
			// 处理业务开始
			// ------------------------------
			String resXml = "";
			if ("SUCCESS".equals((String) packageParams.get("result_code"))) {
				// 这里是支付成功
				String orderNo = (String) packageParams.get("out_trade_no");
				logger.info("微信订单号{}付款成功",orderNo);
				//这里 根据实际业务场景 做相应的操作
				// 通知微信.异步确认成功.必写.不然会一直通知后台.八次之后就认为交易失败了.
				resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			} else {
				logger.info("支付失败,错误信息：{}",packageParams.get("err_code"));
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			}
			// ------------------------------
			// 处理业务完毕
			// ------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} else {
			logger.info("通知签名验证失败");
		}

	}
	/**
	 * 模式一支付回调URL(生成二维码见 qrCodeUtil)
	 * 商户支付回调URL设置指引：进入公众平台-->微信支付-->开发配置-->扫码支付-->修改
	 * @Author  科帮网
	 * @param request
	 * @param response
	 * @throws Exception  void
	 * @Date	2017年8月3日
	 * 更新日志
	 * 2017年8月3日  科帮网 首次创建
	 *
	 */
	@SuppressWarnings({ "unchecked", "rawtypes"})
	@RequestMapping(value = "bizpayurl")
	public void bizpayurl(HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("模式一支付回调URL");
		//读取参数
		InputStream inputStream = request.getInputStream();
		StringBuffer sb = new StringBuffer();
		String s;
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		while ((s = in.readLine()) != null) {
			sb.append(s);
		}
		in.close();
		inputStream.close();
		
		//解析xml成map
		Map<String, String> map = XMLUtil.doXMLParse(sb.toString());
		//过滤空 设置 TreeMap
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		Iterator it = map.keySet().iterator();
		while (it.hasNext()) {
			String parameter = (String) it.next();
			String parameterValue = map.get(parameter);

			String v = "";
			if (null != parameterValue) {
				v = parameterValue.trim();
			}
			packageParams.put(parameter, v);
		}
        //判断签名是否正确
        if (PayCommonUtil.isTenpaySign("UTF-8", packageParams, ConfigUtil.API_KEY)) {
        	//统一下单
            SortedMap<Object, Object> params = new TreeMap<Object, Object>();
    		ConfigUtil.commonParams(params);
    		//随即生成一个 入库 走业务逻辑
    		String out_trade_no=Long.toString(System.currentTimeMillis());
    		params.put("body", "模式一扫码支付");// 商品描述
    		params.put("out_trade_no", out_trade_no);// 商户订单号
    		params.put("total_fee", "100");// 总金额
    		params.put("spbill_create_ip", "192.168.1.66");// 发起人IP地址
    		params.put("notify_url", notify_url);// 回调地址
    		params.put("trade_type", "NATIVE");// 交易类型
    		
    		String paramsSign = PayCommonUtil.createSign("UTF-8", params, ConfigUtil.API_KEY);
    		params.put("sign", paramsSign);// 签名
    		String requestXML = PayCommonUtil.getRequestXml(params);

    		String resXml = HttpUtil.postData(ConfigUtil.UNIFIED_ORDER_URL, requestXML);
    		Map<String, String>  payResult = XMLUtil.doXMLParse(resXml);
    		String returnCode = (String) payResult.get("return_code");
    		if("SUCCESS".equals(returnCode)){
    			String resultCode = (String) payResult.get("result_code");
    			if("SUCCESS".equals(resultCode)){
    				logger.info("(订单号：{}生成微信支付码成功)",out_trade_no);
    				
                    String prepay_id = payResult.get("prepay_id");
                    SortedMap<Object, Object> prepayParams = new TreeMap<Object, Object>();
                    ConfigUtil.commonParams(params);
                    prepayParams.put("prepay_id", prepay_id);
                    prepayParams.put("return_code", "SUCCESS");
                    prepayParams.put("result_code", "SUCCESS");
                    String prepaySign =  PayCommonUtil.createSign("UTF-8", prepayParams, ConfigUtil.API_KEY);
                    prepayParams.put("sign", prepaySign);
                    String prepayXml = PayCommonUtil.getRequestXml(prepayParams);
                    
                    //通知微信 预下单成功
                    BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        			out.write(prepayXml.getBytes());
        			out.flush();
        			out.close();
    			}else{
    				String errCodeDes = (String) map.get("err_code_des");
    				logger.info("(订单号：{}生成微信支付码(系统)失败[{}])",out_trade_no,errCodeDes);
    			}
    		}else{
    			String returnMsg = (String) map.get("return_msg");
    			logger.info("(订单号：{} 生成微信支付码(通信)失败[{}])",out_trade_no,returnMsg);
    		}
        }else{
        	logger.info("签名错误");
        }
	}
}
