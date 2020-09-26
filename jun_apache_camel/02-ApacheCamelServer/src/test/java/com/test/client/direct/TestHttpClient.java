package com.test.client.direct;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;

public class TestHttpClient {

	public static void main(String[] args) throws Exception {

		CloseableHttpClient httpClient = HttpClients.createDefault();

		HttpPost httpPost = new HttpPost("http://0.0.0.0:8282/directMain");

		// 添加参数
		JSONObject authorityJson = new JSONObject();
		authorityJson.put("routeName", "direct:directRouteB,direct:directRouteC");

		JSONObject requestJson = new JSONObject();
		requestJson.put("data", authorityJson);
		requestJson.put("token", "d9c33c8f-ae59-4edf-b37f-290ff208de2e");
		requestJson.put("desc", "oasdjosjdsidjfisodjf");

		StringBuffer sbb = new StringBuffer();
		sbb.append(requestJson.toString());

		httpPost.setHeader("ContentType", "UTF-8");
		StringEntity urlEntity = new StringEntity(sbb.toString(), "UTF-8");
		httpPost.setEntity(urlEntity);

		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();

		InputStream inputStream = entity.getContent();
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

		// 读取HTTP请求内容
		String buffer = null;
		StringBuffer sb = new StringBuffer();
		while ((buffer = br.readLine()) != null) {
			// 在页面中显示读取到的请求参数
			sb.append(buffer + "\n");
		}

		System.out.println("接收返回数据:\n" + URLDecoder.decode(sb.toString().trim(), "UTF-8"));

		System.out.println("Login form get: " + response.getStatusLine() + entity.getContent());

	}

}
