package com.jun.plugin.zxing;

import java.util.HashMap;
import java.util.Map;
/**
 * 生成短链接 、自行配置后端服务 https://blog.52itstyle.com/archives/2563/
 * @author 小柒2012
 */
public class ShortUrlUtil {
	
	public static String urlToRequest = "https://polr.52itstyle.com/api/v2/action/shorten";
	
	public static String createShortUrl(String url){
		 Map<String, String> parameters = new HashMap<String, String>();
		 parameters.put("key", "0");
		 parameters.put("url", url);
		 return HttpClientHelper.requestBodyString(urlToRequest, parameters);
	}
    public static void main(String[] args) {
    	String url = "https://www.baid.com/index.html";
    	url = createShortUrl(url);
    	System.out.println(url);
    	System.out.println("生成二维码地址:D:\\createShortUrl.png");
    	QrcodeUtil.createQrcode(200,200,url,"png","D:\\createShortUrl.png");
	}
}
