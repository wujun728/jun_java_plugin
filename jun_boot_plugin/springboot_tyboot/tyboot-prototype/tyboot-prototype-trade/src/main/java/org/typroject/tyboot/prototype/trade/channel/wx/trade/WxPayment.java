package org.typroject.tyboot.prototype.trade.channel.wx.trade;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.typroject.tyboot.core.foundation.context.RequestContext;
import org.typroject.tyboot.core.foundation.utils.Encrypt;
import org.typroject.tyboot.core.foundation.utils.ValidationUtil;
import org.typroject.tyboot.face.trade.model.TransactionsSerialModel;
import org.typroject.tyboot.prototype.trade.Trade;
import org.typroject.tyboot.prototype.trade.TradeResultModel;
import org.typroject.tyboot.prototype.trade.PropertyConstants;
import org.typroject.tyboot.prototype.trade.channel.wx.WxpayProperty;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

@Component(value = "wxPayment")
public class WxPayment implements Trade{

	
	private static final Logger logger = LoggerFactory.getLogger(WxPayment.class);

	@Autowired
	private WxpayProperty wxpayProperty;

	private String configur(TransactionsSerialModel serialModel, Map<String, Object> extra) throws Exception
	{
		String xmlParams = "";

		SortedMap<String,Object> params  = new TreeMap<>();

		params.put("appid",wxpayProperty.getAppid());//应用ID
		params.put("mch_id",wxpayProperty.getMchid());//商户号
		params.put("nonce_str", Encrypt.md5WithoutPadding(serialModel.getSerialNo()));//随机字符串

		params.put("body",extra.get(PropertyConstants.PAYMENT_SUBJECT));//商品描述
		params.put("attach",serialModel.getSerialNo());//附加数据
		params.put("out_trade_no",serialModel.getSerialNo());//商户订单号
		//params.put("total_fee",serialModel.getTradeAmount().multiply(new BigDecimal(100)).intValue());//总金额
		params.put("total_fee",serialModel.getTradeAmount().multiply(new BigDecimal(100)).intValue());//总金额
		params.put("spbill_create_ip", RequestContext.getRequestIP());//终端IP
		params.put("notify_url",wxpayProperty.getNotifyUrl());//通知地址
		params.put("trade_type","APP");//交易类型	APP

		params.put("sign",sign(params));//签名

		xmlParams = map2XmlString(params);

		return xmlParams;
	}


	
	@Override
	public TradeResultModel process(TransactionsSerialModel serialModel, Map<String, Object> extra) throws Exception {

		String timestamp = String.valueOf(System.currentTimeMillis()/1000);
		TradeResultModel resultModel  = new TradeResultModel();
		String resultXml = this.sendHttpsCoon(wxpayProperty.getPaymentUrl(),this.configur(serialModel,extra ));



		if(!ValidationUtil.isEmpty(resultXml))
		{
			Map map = readStringXmlOut(resultXml);

			if("SUCCESS".equals(map.get("return_code")) && "SUCCESS".equals(map.get("result_code")))
			{


				SortedMap<String,Object> params2Sign  = new TreeMap<>();

				params2Sign.put("appid",map.get("appid"));
				params2Sign.put("partnerid",map.get("mch_id"));
				params2Sign.put("prepayid",map.get("prepay_id"));
				params2Sign.put("noncestr",map.get("nonce_str"));
				params2Sign.put("timestamp",timestamp);
				params2Sign.put("package","Sign=WXPay");

				params2Sign.put("sign",sign(params2Sign));
				resultModel.setResult(params2Sign);
				resultModel.setCalledSuccess(true);
			}else
			{
				logger.info((String)map.get("err_code"));
				logger.info((String)map.get("err_code_des"));
				resultModel.setCalledSuccess(false);
			}
		}else
		{
			resultModel.setCalledSuccess(false);
		}
		return resultModel;
	}





	/*public static void main(String []args)
	{
		logger.info(Encrypt.md5WithoutPadding("smsns20171548121548"));
	}*/


	public static String map2XmlString(Map<String, Object> map) {
		String xmlResult = "";

		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		for (String key : map.keySet()) {
			String value = "<![CDATA[" + map.get(key) + "]]>";
			sb.append("<" + key + ">" + value + "</" + key + ">");
		}
		sb.append("</xml>");
		xmlResult = sb.toString();

		return xmlResult;
	}


	/**
	 * @description 将xml字符串转换成map
	 * @param xml
	 * @return Map
	 */
	public static Map<String, String> readStringXmlOut(String xml) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		Document doc = null;
		doc = DocumentHelper.parseText(xml); // 将字符串转为XML
		Element rootElt = doc.getRootElement(); // 获取根节点
		@SuppressWarnings("unchecked")
		List<Element> list = rootElt.elements();// 获取根节点下所有节点
		for (Element element : list) { // 遍历节点
			map.put(element.getName(), element.getText()); // 节点的name为map的key，text为map的value
		}
		return map;
	}


	public String sign(SortedMap<String,Object>  sortedMap) throws Exception
	{

		String params = "";
		for(String key : sortedMap.keySet() )
		{
			params += key+"="+sortedMap.get(key)+"&";
		}

		params =  params+"key="+wxpayProperty.getPaymentKey();

		return md5(params).toUpperCase();
	}


	/**
	 * md5加密方法
	 * @author: zhengsunlei
	 * Jul 30, 2010 4:38:28 PM
	 * @param plainText 加密字符串
	 * @return String 返回32位md5加密字符串(16位加密取substring(8,24))
	 * 每位工程师都有保持代码优雅的义务
	 * each engineer has a duty to keep the code elegant
	 */
	public final static String md5(String plainText) throws Exception {
		// 返回字符串
		String md5Str = null;
		// 操作字符串
		StringBuffer buf = new StringBuffer();
		/**
		 * MessageDigest 类为应用程序提供信息摘要算法的功能，如 MD5 或 SHA 算法。
		 * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
		 *
		 * MessageDigest 对象开始被初始化。
		 * 该对象通过使用 update()方法处理数据。
		 * 任何时候都可以调用 reset()方法重置摘要。
		 * 一旦所有需要更新的数据都已经被更新了，应该调用digest()方法之一完成哈希计算。
		 *
		 * 对于给定数量的更新数据，digest 方法只能被调用一次。
		 * 在调用 digest 之后，MessageDigest 对象被重新设置成其初始状态。
		 */
		MessageDigest md = MessageDigest.getInstance("MD5");

		// 添加要进行计算摘要的信息,使用 plainText 的 byte 数组更新摘要。
		md.update(plainText.getBytes());
		// 计算出摘要,完成哈希计算。
		byte b[] = md.digest();
		int i;
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0) {
				i += 256;
			}
			if (i < 16) {
				buf.append("0");
			}
			// 将整型 十进制 i 转换为16位，用十六进制参数表示的无符号整数值的字符串表示形式。
			buf.append(Integer.toHexString(i));
		}
		// 32位的加密
		md5Str = buf.toString();
		// 16位的加密
		// md5Str = buf.toString().md5Strstring(8,24);
		return md5Str;
	}


	/**
	 * 发送请求
	 * @param url
	 * @param
	 * @return
	 */
	private String sendHttpsCoon(String url, String code) throws Exception{
		if(url.isEmpty()){
			return null;
		}
			//设置SSLContext
			SSLContext ssl = SSLContext.getInstance("SSL");
			ssl.init(null, new TrustManager[]{myX509TrustManager}, null);

			//打开连接
			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			//设置套接工厂
			conn.setSSLSocketFactory(ssl.getSocketFactory());
			//加入数据
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			//conn.setRequestProperty("Content-type","application/json");

			BufferedOutputStream buffOutStr = new BufferedOutputStream(conn.getOutputStream());
			buffOutStr.write(code.getBytes());
			buffOutStr.flush();
			buffOutStr.close();

			//获取输入流
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

			String line = null;
			StringBuffer sb = new StringBuffer();
			while((line = reader.readLine())!= null){
				sb.append(line);
			}
			return sb.toString();
	}


	/**
	 * 重写X509TrustManager
	 */
	private static TrustManager myX509TrustManager = new X509TrustManager() {
		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return null;
		}
		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

		}
	};
	
	
}
