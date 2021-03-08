package com.itstyle.modules.unionpay.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itstyle.common.constants.PayWay;
import com.itstyle.common.model.Product;
import com.itstyle.modules.alipay.controller.AliPayController;
import com.itstyle.modules.unionpay.service.IUnionPayService;
import com.itstyle.modules.unionpay.util.AcpService;
import com.itstyle.modules.unionpay.util.SDKConstants;
/**
 * 银联支付
 * 创建者 科帮网
 * 创建时间	2017年8月2日
 *
 */
@Controller
@RequestMapping(value = "unionpay")
public class UnionPayController {
	private static final Logger logger = LoggerFactory.getLogger(AliPayController.class);
	
	@Autowired
	private IUnionPayService unionPayService;

	@RequestMapping("/index")
    public String   index() {
        return "unionpay/index";
    }
	
	@RequestMapping("/pcPay")
    public String  pcPay(Product product,ModelMap map) {
		logger.info("电脑支付");
		product.setPayWay(PayWay.PC.getCode());
		String form  =  unionPayService.unionPay(product);
		map.addAttribute("form", form);
		return "unionpay/pay";
    }
	@RequestMapping("/mobilePay")
    public String  mobilePay(Product product,ModelMap map) {
		logger.info("手机H5支付");
		product.setPayWay(PayWay.MOBILE.getCode());
		String form  =  unionPayService.unionPay(product);
		map.addAttribute("form", form);
		return "unionpay/pay";
    }
	
	/**
	 * 其实我小时候的梦想并不是要当什么程序员，
     * 我只是幻想自己是地主家的少爷，家有良田万顷，
     * 终日不学无术，没事领着一群狗奴才上街去调戏一下良家少女。
     * 然后这个方法的基本作用就是 银联支付回调 通知我们支付是否成功。
	 * @Author  科帮网
	 * @param request
	 * @param response
	 * @throws Exception  void
	 * @Date	2017年8月2日
	 * 更新日志
	 * 2017年8月2日  科帮网 首次创建
	 *
	 */
	@RequestMapping(value = "pay")
	public void union_notify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        logger.info("银联接收后台通知开始");
		String encoding = request.getParameter(SDKConstants.param_encoding);
		// 获取银联通知服务器发送的后台通知参数
		Map<String, String> reqParam = getAllRequestParam(request);
		//打印参数
		logger.info(reqParam.toString());
		Map<String, String> valideData = null;
		if (null != reqParam && !reqParam.isEmpty()) {
			Iterator<Entry<String, String>> it = reqParam.entrySet().iterator();
			valideData = new HashMap<String, String>(reqParam.size());
			while (it.hasNext()) {
				Entry<String, String> e = it.next();
				String key = (String) e.getKey();
				String value = (String) e.getValue();
				value = new String(value.getBytes(encoding), encoding);
				valideData.put(key, value);
			}
		}
		//重要！验证签名前不要修改reqParam中的键值对的内容，否则会验签不过
		if (!AcpService.validate(valideData, encoding)) {
			logger.info("银联验证签名结果[失败].");
		} else {
			logger.info("银联验证签名结果[成功].");
			String outtradeno =valideData.get("orderId");//订单号
			String reqReserved = valideData.get("reqReserved");//辅助信息(字段穿透)
			logger.info("处理相关业务逻辑{},{}",outtradeno,reqReserved);
			response.getWriter().print("ok");//返回给银联服务器http 200  状态码
		}
	}
	/**
	 * 获取请求参数中所有的信息
	 * @Author  科帮网
	 * @param request
	 * @return  Map<String,String>
	 * @Date	2017年8月2日
	 * 更新日志
	 * 2017年8月2日  科帮网 首次创建
	 *
	 */
	public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
		Map<String, String> res = new HashMap<String, String>();
		Enumeration<?> temp = request.getParameterNames();
		if (null != temp) {
			while (temp.hasMoreElements()) {
				String en = (String) temp.nextElement();
				String value = request.getParameter(en);
				res.put(en, value);
				//在报文上送时，如果字段的值为空，则不上送<下面的处理为在获取所有参数数据时，判断若值为空，则删除这个字段>
				//System.out.println("ServletUtil类247行  temp数据的键=="+en+"     值==="+value);
				if (null == res.get(en) || "".equals(res.get(en))) {
					res.remove(en);
				}
			}
		}
		return res;
	}
}
