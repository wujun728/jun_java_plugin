package com.open1111.httpclient.chap02;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Demo3 {

	public static void main(String[] args)throws Exception {
		CloseableHttpClient httpClient=HttpClients.createDefault(); // 创建httpClient实例
		HttpGet httpGet=new HttpGet("http://www.open1111.com/"); // 创建httpget实例
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		CloseableHttpResponse response=httpClient.execute(httpGet); // 执行http get请求
		System.out.println("Status:"+response.getStatusLine().getStatusCode());
		HttpEntity entity=response.getEntity(); // 获取返回实体
		System.out.println("Content-Type:"+entity.getContentType().getValue());
		//System.out.println("网页内容："+EntityUtils.toString(entity, "utf-8")); // 获取网页内容
		response.close(); // response关闭
		httpClient.close(); // httpClient关闭
	}
}
