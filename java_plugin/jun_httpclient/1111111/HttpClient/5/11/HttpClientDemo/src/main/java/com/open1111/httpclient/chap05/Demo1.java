package com.open1111.httpclient.chap05;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Demo1 {

	public static void main(String[] args)throws Exception {
		CloseableHttpClient httpClient=HttpClients.createDefault(); // 创建httpClient实例
		HttpGet httpGet=new HttpGet("http://central.maven.org/maven2/"); // 创建httpget实例
		RequestConfig config=RequestConfig.custom()
				.setConnectTimeout(10000) // 设置连接超时时间 10秒钟
				.setSocketTimeout(20000) // 设置读取超时时间10秒钟
				.build();
		httpGet.setConfig(config);
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		CloseableHttpResponse response=httpClient.execute(httpGet); // 执行http get请求
		HttpEntity entity=response.getEntity(); // 获取返回实体
		System.out.println("网页内容："+EntityUtils.toString(entity, "utf-8")); // 获取网页内容
		response.close(); // response关闭
		httpClient.close(); // httpClient关闭
	}
}
