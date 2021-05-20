package com.open1111.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HelloWorld2 {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=HttpClients.createDefault(); // 创建httpClient实例
		HttpGet httpGet=new HttpGet("http://www.java1234.com/"); // 创建httpget实例
		CloseableHttpResponse response=httpClient.execute(httpGet); // 执行http get请求
		HttpEntity entity=response.getEntity(); // 获取返回实体
		System.out.println("网页内容："+EntityUtils.toString(entity, "utf-8")); // 获取网页内容
		response.close(); // response关闭
		httpClient.close(); // httpClient关闭
	}
}
