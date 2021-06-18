package com.open1111.httpclient;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HelloWorld {

	public static void main(String[] args) {
		CloseableHttpClient httpClient=HttpClients.createDefault(); // 创建httpClient实例
		HttpGet httpGet=new HttpGet("http://www.tuicool.com/"); // 创建httpget实例
		CloseableHttpResponse response=null;
		try {
			response=httpClient.execute(httpGet); // 执行http get请求
		} catch (ClientProtocolException e) { // http协议异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) { // io异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		HttpEntity entity=response.getEntity(); // 获取返回实体
		try {
			System.out.println("网页内容："+EntityUtils.toString(entity, "utf-8")); // 获取网页内容
		} catch (ParseException e) { // 解析异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) { // io异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			response.close(); // response关闭
		} catch (IOException e) {  // io异常
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		try {
			httpClient.close(); // httpClient关闭
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
