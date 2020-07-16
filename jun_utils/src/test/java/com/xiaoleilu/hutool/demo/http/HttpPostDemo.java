package com.xiaoleilu.hutool.demo.http;

import java.util.HashMap;

import com.jun.plugin.utils2.http.Header;
import com.jun.plugin.utils2.http.HttpRequest;
import com.jun.plugin.utils2.http.HttpUtil;
import com.jun.plugin.utils2.lang.Console;

/**
 * Post请求样例
 * @author Looly
 *
 */
public class HttpPostDemo {
	public static void main(String[] args) {
		String url = "http://wthrcdn.etouch.cn/weather_mini";
		
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("city", "北京");
		String result1 = HttpUtil.post(url, paramMap);
		Console.log(result1);
		
		//链式构建请求
		String result2 = HttpRequest.post(url)
			.header(Header.USER_AGENT, "Hutool http")
			.form(paramMap)
			.execute().body();
		Console.log(result2);
	}
}
