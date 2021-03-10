package com.itstyle.modules.weixinpay.service.impl;

import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.alibaba.dubbo.config.annotation.Service;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.itstyle.common.constants.Constants;
import com.itstyle.common.model.Product;
import com.itstyle.common.utils.CommonUtil;
import com.itstyle.modules.weixinpay.service.IWeixinPayService;
import com.itstyle.modules.weixinpay.util.ClientCustomSSL;
import com.itstyle.modules.weixinpay.util.ConfigUtil;
import com.itstyle.modules.weixinpay.util.HttpUtil;
import com.itstyle.modules.weixinpay.util.PayCommonUtil;
import com.itstyle.modules.weixinpay.util.XMLUtil;
@Service
public class WeixinPayServiceImpl implements IWeixinPayService {
	private static final Logger logger = LoggerFactory.getLogger(WeixinPayServiceImpl.class);
	
	@Value("${wexinpay.notify.url}")
	private String notify_url;
	@Value("${server.context.url}")
	private String server_url;
	
	@SuppressWarnings("rawtypes")
	@Override
	public String weixinPay(Product product) {
		logger.info("订单号：{}生成微信支付码",product.getOutTradeNo());
		String  message = Constants.SUCCESS;
		try {
			String imgPath= Constants.QRCODE_PATH+Constants.SF_FILE_SEPARATOR+product.getOutTradeNo()+".png";
			// 账号信息
			String key = ConfigUtil.API_KEY; // key
			String trade_type = "NATIVE";// 交易类型 原生扫码支付
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			ConfigUtil.commonParams(packageParams);
			packageParams.put("product_id", product.getProductId());// 商品ID
			packageParams.put("body", product.getBody());// 商品描述
			packageParams.put("out_trade_no", product.getOutTradeNo());// 商户订单号
			String totalFee = product.getTotalFee();
			totalFee =  CommonUtil.subZeroAndDot(totalFee);
			packageParams.put("total_fee", totalFee);// 总金额
			packageParams.put("spbill_create_ip", product.getSpbillCreateIp());// 发起人IP地址
			packageParams.put("notify_url", notify_url);// 回调地址
			packageParams.put("trade_type", trade_type);// 交易类型
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名

			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(ConfigUtil.UNIFIED_ORDER_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if("SUCCESS".equals(returnCode)){
				String resultCode = (String) map.get("result_code");
				if("SUCCESS".equals(resultCode)){
					logger.info("订单号：{}生成微信支付码成功",product.getOutTradeNo());
					String urlCode = (String) map.get("code_url");
					ConfigUtil.shorturl(urlCode);//转换为短链接
					ZxingUtils.getQRCodeImge(urlCode, 256, imgPath);// 生成二维码
				}else{
					String errCodeDes = (String) map.get("err_code_des");
					logger.info("订单号：{}生成微信支付码(系统)失败:{}",product.getOutTradeNo(),errCodeDes);
					message = Constants.FAIL;
				}
			}else{
				String returnMsg = (String) map.get("return_msg");
				logger.info("(订单号：{}生成微信支付码(通信)失败:{}",product.getOutTradeNo(),returnMsg);
				message = Constants.FAIL;
			}
		} catch (Exception e) {
			logger.error("订单号：{}生成微信支付码失败(系统异常))",product.getOutTradeNo(),e);
			message = Constants.FAIL;
		}
		return message;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public String weixinRefund(Product product) {
		logger.info("订单号：{}微信退款",product.getOutTradeNo());
		String  message = Constants.SUCCESS;
		try {
			// 账号信息
			String mch_id = ConfigUtil.MCH_ID; // 商业号
			String key = ConfigUtil.API_KEY; // key
			
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			ConfigUtil.commonParams(packageParams);
			packageParams.put("out_trade_no", product.getOutTradeNo());// 商户订单号
			packageParams.put("out_refund_no", product.getOutTradeNo());//商户退款单号
			String totalFee = product.getTotalFee();
			totalFee =  CommonUtil.subZeroAndDot(totalFee);
			packageParams.put("total_fee", totalFee);// 总金额
			packageParams.put("refund_fee", totalFee);//退款金额
			packageParams.put("op_user_id", mch_id);//操作员帐号, 默认为商户号
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String weixinPost = ClientCustomSSL.doRefund(ConfigUtil.REFUND_URL, requestXML).toString(); 
			Map map = XMLUtil.doXMLParse(weixinPost);
			String returnCode = (String) map.get("return_code");
			if("SUCCESS".equals(returnCode)){
				String resultCode = (String) map.get("result_code");
				if("SUCCESS".equals(resultCode)){
					logger.info("订单号：{}微信退款成功并删除二维码",product.getOutTradeNo());
				}else{
					String errCodeDes  = (String) map.get("err_code_des");
					logger.info("订单号：{}微信退款失败:{}",product.getOutTradeNo(),errCodeDes);
					message = Constants.FAIL;
				}
			}else{
				String returnMsg = (String) map.get("return_msg");
				logger.info("订单号：{}微信退款失败:{}",product.getOutTradeNo(),returnMsg);
				message = Constants.FAIL;
			}
		} catch (Exception e) {
			logger.error("订单号：{}微信支付失败(系统异常)",product.getOutTradeNo(), e);
			message = Constants.FAIL;
		}
		return message;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String weixinCloseorder(Product product) {
		logger.info("订单号：{}微信关闭订单",product.getOutTradeNo());
		String  message = Constants.SUCCESS;
		try {
			String key = ConfigUtil.API_KEY; // key
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			ConfigUtil.commonParams(packageParams);
			packageParams.put("out_trade_no", product.getOutTradeNo());// 商户订单号
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(ConfigUtil.CLOSE_ORDER_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if("SUCCESS".equals(returnCode)){
				String resultCode =  (String) map.get("result_code");
				if("SUCCESS".equals(resultCode)){
					logger.info("订单号：{}微信关闭订单成功",product.getOutTradeNo());
				}else{
					String errCode =  (String) map.get("err_code");
					String errCodeDes =  (String) map.get("err_code_des");
					if("ORDERNOTEXIST".equals(errCode)||"ORDERCLOSED".equals(errCode)){//订单不存在或者已经关闭
						logger.info("订单号：{}微信关闭订单:{}",product.getOutTradeNo(),errCodeDes);
					}else{
						logger.info("订单号：{}微信关闭订单失败:{}",product.getOutTradeNo(),errCodeDes);
						message = Constants.FAIL;
					}
				}
			}else{
				String returnMsg = (String) map.get("return_msg");
				logger.info("订单号：{}微信关闭订单失败:{}",product.getOutTradeNo(),returnMsg);
				message = Constants.FAIL;
			}
		} catch (Exception e) {
			logger.error("订单号：{}微信关闭订单失败(系统异常)", product.getOutTradeNo(),e);
			message = Constants.FAIL;
		}
		return message;
	}
	/**
	 * 商户可以通过该接口下载历史交易清单。比如掉单、系统错误等导致商户侧和微信侧数据不一致，通过对账单核对后可校正支付状态。
		注意：
		1、微信侧未成功下单的交易不会出现在对账单中。支付成功后撤销的交易会出现在对账单中，跟原支付单订单号一致，bill_type为REVOKED；
		2、微信在次日9点启动生成前一天的对账单，建议商户10点后再获取；
		3、对账单中涉及金额的字段单位为“元”。
		
		4、对账单接口只能下载三个月以内的账单。
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void saveBill() {
		try {
			String key = ConfigUtil.API_KEY; // key
			//获取两天以前的账单
			//String billDate = DateUtil.getBeforeDayDate("2");
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			ConfigUtil.commonParams(packageParams);//公用部分
			packageParams.put("bill_type", "ALL");//ALL，返回当日所有订单信息，默认值SUCCESS，返回当日成功支付的订单REFUND，返回当日退款订单
			//packageParams.put("tar_type", "GZIP");//压缩账单
			packageParams.put("bill_date", "20161206");//账单日期
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名
			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(ConfigUtil.DOWNLOAD_BILL_URL, requestXML);
            if(resXml.startsWith("<xml>")){
            	Map map = XMLUtil.doXMLParse(resXml);
    			String returnMsg = (String) map.get("return_msg");
    			logger.info("微信查询订单失败:{}",returnMsg);
			}else{
				 //入库
			}
		} catch (Exception e) {
			logger.error("微信查询订单异常", e);
		}
		
	}
	@Override
	public String weixinPayMobile(Product product) {
		StringBuffer url = new StringBuffer();
		String totalFee = product.getTotalFee();
		//redirect_uri 需要在微信支付端添加认证网址
		totalFee =  CommonUtil.subZeroAndDot(totalFee);
		url.append("http://open.weixin.qq.com/connect/oauth2/authorize?");
		url.append("appid="+ConfigUtil.APP_ID);
		url.append("&redirect_uri="+server_url+"weixinMobile/dopay");
		url.append("&response_type=code&scope=snsapi_base&state=");
		url.append(product.getOutTradeNo()+"/"+totalFee);//订单号/金额(单位是分)
		url.append("#wechat_redirect");
		return  url.toString();
	}
	@SuppressWarnings("rawtypes")
	@Override
	public String weixinPayH5(Product product) {
		logger.info("订单号：{}发起H5支付",product.getOutTradeNo());
		String  mweb_url = "";
		try {
			// 账号信息
			String key = ConfigUtil.API_KEY; // key
			String trade_type = "MWEB";//交易类型 H5 支付 
			SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
			ConfigUtil.commonParams(packageParams);
			packageParams.put("product_id", product.getProductId());// 商品ID
			packageParams.put("body", product.getBody());// 商品描述
			packageParams.put("out_trade_no", product.getOutTradeNo());// 商户订单号
			String totalFee = product.getTotalFee();
			totalFee =  CommonUtil.subZeroAndDot(totalFee);
			packageParams.put("total_fee", totalFee);// 总金额
			//H5支付要求商户在统一下单接口中上传用户真实ip地址 spbill_create_ip
			packageParams.put("spbill_create_ip", product.getSpbillCreateIp());// 发起人IP地址
			packageParams.put("notify_url", notify_url);// 回调地址
			packageParams.put("trade_type", trade_type);// 交易类型
			//H5支付专用 
			JSONObject value = new JSONObject();
			value.put("type", "WAP");
			value.put("wap_url", "https://blog.52itstyle.com");////WAP网站URL地址
			value.put("wap_name", "科帮网充值");//WAP 网站名
			JSONObject scene_info = new JSONObject();
			scene_info.put("h5_info", value);
			packageParams.put("scene_info", scene_info.toString());
			
			String sign = PayCommonUtil.createSign("UTF-8", packageParams, key);
			packageParams.put("sign", sign);// 签名

			String requestXML = PayCommonUtil.getRequestXml(packageParams);
			String resXml = HttpUtil.postData(ConfigUtil.UNIFIED_ORDER_URL, requestXML);
			Map map = XMLUtil.doXMLParse(resXml);
			String returnCode = (String) map.get("return_code");
			if("SUCCESS".equals(returnCode)){
				String resultCode = (String) map.get("result_code");
				if("SUCCESS".equals(resultCode)){
					logger.info("订单号：{}发起H5支付成功",product.getOutTradeNo());
					mweb_url = (String) map.get("mweb_url");
				}else{
					String errCodeDes = (String) map.get("err_code_des");
					logger.info("订单号：{}发起H5支付(系统)失败:{}",product.getOutTradeNo(),errCodeDes);
				}
			}else{
				String returnMsg = (String) map.get("return_msg");
				logger.info("(订单号：{}发起H5支付(通信)失败:{}",product.getOutTradeNo(),returnMsg);
			}
		} catch (Exception e) {
			logger.error("订单号：{}发起H5支付失败(系统异常))",product.getOutTradeNo(),e);
		}
		return mweb_url;
	}
}
