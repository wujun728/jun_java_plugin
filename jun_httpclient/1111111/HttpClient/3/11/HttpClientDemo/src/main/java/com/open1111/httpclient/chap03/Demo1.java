package com.open1111.httpclient.chap03;

import java.io.File;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class Demo1 {

	public static void main(String[] args)throws Exception {
		CloseableHttpClient httpClient=HttpClients.createDefault(); // 创建httpClient实例
		HttpGet httpGet=new HttpGet("http://www.java1234.com/gg/dljd4.gif"); // 创建httpget实例
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		CloseableHttpResponse response=httpClient.execute(httpGet); // 执行http get请求
		HttpEntity entity=response.getEntity(); // 获取返回实体
		if(entity!=null){
			System.out.println("ContentType:"+entity.getContentType().getValue());
			InputStream inputStream=entity.getContent();
			FileUtils.copyToFile(inputStream, new File("C://dljd4.gif"));
		}
		response.close(); // response关闭
		httpClient.close(); // httpClient关闭
	}
}
