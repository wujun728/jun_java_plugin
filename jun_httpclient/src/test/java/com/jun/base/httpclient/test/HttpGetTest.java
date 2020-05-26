package com.jun.base.httpclient.test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpGetTest {

	@Test
	public  void getPageContent() throws ClientProtocolException, IOException {
		CloseableHttpClient httpClient=HttpClients.createDefault(); 
		HttpGet httpGet=new HttpGet("http://www.baidu.com/");
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		CloseableHttpResponse response=httpClient.execute(httpGet);
		HttpEntity entity=response.getEntity(); 
		System.out.println("内容："+EntityUtils.toString(entity, "utf-8")); 
		response.close(); 
		httpClient.close(); 
		 
	}
	

	@Test
	public  void getPageImage() throws ClientProtocolException, IOException  {
		CloseableHttpClient httpClient=HttpClients.createDefault(); // ����httpClientʵ��
		HttpGet httpGet=new HttpGet("https://www.baidu.com/img/pc_cc75653cd975aea6d4ba1f59b3697455.png"); 
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		CloseableHttpResponse response=httpClient.execute(httpGet); 
		HttpEntity entity=response.getEntity(); 
		if(entity!=null){
			System.out.println("ContentType:"+entity.getContentType().getValue());
			InputStream inputStream=entity.getContent();
			FileUtils.copyToFile(inputStream, new File("E://baidu.png"));
		}
		response.close(); 
		httpClient.close(); 
	}
	
	
	@Test
	public  void setPageProxyTest() throws ClientProtocolException, IOException   {
		CloseableHttpClient httpClient=HttpClients.createDefault(); 
		HttpGet httpGet=new HttpGet("http://www.tuicool.com/"); 
		HttpHost proxy=new HttpHost("119.27.170.46", 8888);   // http://31f.cn/
		RequestConfig config=RequestConfig.custom().setProxy(proxy).setConnectTimeout(10000).setSocketTimeout(20000).build();
		httpGet.setConfig(config);
		httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
		CloseableHttpResponse response=httpClient.execute(httpGet); 
		HttpEntity entity=response.getEntity(); 
		System.out.println("ContentType "+EntityUtils.toString(entity, "utf-8")); 
		response.close(); 
		httpClient.close(); 
	}
}
