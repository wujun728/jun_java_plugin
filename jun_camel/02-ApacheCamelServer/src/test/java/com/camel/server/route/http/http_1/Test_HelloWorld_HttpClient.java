package com.camel.server.route.http.http_1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class Test_HelloWorld_HttpClient {

	public static void main(String[] args) {

		// 获取可关闭的 httpCilent
		CloseableHttpClient httpClient = HttpClients.createDefault();

		// 配置超时时间
		RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(1000).setConnectionRequestTimeout(1000)
				.setSocketTimeout(1000).setRedirectsEnabled(true).build();

		HttpPost httpPost = new HttpPost("http://127.0.0.1:8282/doHelloWorld");

		// 设置超时时间
		httpPost.setConfig(requestConfig);

		// 装配post请求参数
		List<BasicNameValuePair> list = new ArrayList<BasicNameValuePair>();
		list.add(new BasicNameValuePair("age", "28496121543151510")); // 请求参数
		list.add(new BasicNameValuePair("name", "zhang81654841618489san")); // 请求参数

		try {

			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");

			// 设置post求情参数
			httpPost.setEntity(entity);

			HttpResponse httpResponse = httpClient.execute(httpPost);

			String strResult = "";

			if (httpResponse != null) {

				System.out.println(httpResponse.getStatusLine().getStatusCode());

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					strResult = EntityUtils.toString(httpResponse.getEntity());
				} else if (httpResponse.getStatusLine().getStatusCode() == 400) {
					strResult = "Error Response: " + httpResponse.getStatusLine().toString();
				} else if (httpResponse.getStatusLine().getStatusCode() == 500) {
					strResult = "Error Response: " + httpResponse.getStatusLine().toString();
				} else {
					strResult = "Error Response: " + httpResponse.getStatusLine().toString();
				}
			} else {

			}

			System.out.println(strResult);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (httpClient != null) {
					httpClient.close(); // 释放资源
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
