package com.itstyle.modules.weixinpay.util;
import java.util.SortedMap;
import java.util.TreeMap;

import com.alipay.demo.trade.utils.ZxingUtils;
/**
 * 二维码生成器(扫码支付模式一)
 * 创建者  小柒2012 https://blog.52itstyle.com/
 * 创建时间	2017年8月2日
 */
public class qrCodeUtil {
	//商户支付回调URL设置指引：进入公众平台-->微信支付-->开发配置-->扫码支付-->修改 加入回调URL
    public static void main(String[] args) {
    	//注意参数初始化 这只是个Demo
		SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
		//封装通用参数
		ConfigUtil.commonParams(packageParams);
		packageParams.put("product_id", "20170731");//真实商品ID
		packageParams.put("time_stamp", PayCommonUtil.getCurrTime());
		//生成签名
		String sign = PayCommonUtil.createSign("UTF-8", packageParams, ConfigUtil.API_KEY);
		//组装二维码信息(注意全角和半角：的区别 狗日的腾讯)
    	StringBuffer qrCode = new StringBuffer();
    	qrCode.append("weixin://wxpay/bizpayurl?");
    	qrCode.append("appid="+ConfigUtil.APP_ID);
    	qrCode.append("&mch_id="+ConfigUtil.MCH_ID);
    	qrCode.append("&nonce_str="+packageParams.get("nonce_str"));
    	qrCode.append("&product_id=20170731");
    	qrCode.append("&time_stamp="+packageParams.get("time_stamp"));
    	qrCode.append("&sign="+sign);
    	//生成二维码
        ZxingUtils.getQRCodeImge(qrCode.toString(), 256, "D:\\weixn.png");
	}
}