// This file is commented out — OkHttp wrapper moved to jun_okhttp module.
/*
package com.jun.plugin.okhttp.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;

import javax.net.ssl.SSLContext;

import com.jun.plugin.okhttp.FastHttpClient;
import com.jun.plugin.okhttp.Response;


/**
 *
 * @author Wujun
 *
 * /
public class URLUtil {
	//
	public static String httpGet(String url) throws Exception {
		Response response = FastHttpClient.get().
				url(url).
				build().
				execute();
		return response.body().string();
	}
	//
	public static String httpsGet(String url) throws Exception {
		Response response = FastHttpClient.get().
				url(url).
				build().
				execute();
		return response.body().string();
	}
	//
	public static String httpsGet(String url,SSLContext sslContext) throws Exception {
		Response response = FastHttpClient.get().
				url(url).
				build().
				sslContext(sslContext).
				execute();
		return response.body().string();
	}
	//
	public static String httpPost(String url,Map<String,String> paramMap) throws MalformedURLException, IOException{
		Response response = FastHttpClient.post().
				url(url).
				addParams(paramMap).
				build().
				execute();
		return response.body().string();
	}
	//
	public static String httpPostWithBody(String url,String body) throws MalformedURLException, IOException{
		Response response = FastHttpClient.post().
				url(url).
				body(body).
				build().
				execute();
		return response.body().string();
	}
	//
	public static String httpsPost(String url) throws MalformedURLException, IOException{
		return httpsPost(url, null, null);
	}
	//
	public static String httpsPost(String url,Map<String,String> paramMap) throws MalformedURLException, IOException{
		return httpsPost(url, paramMap, null);
	}
	//
	public static String httpsPost(String url,Map<String,String> paramMap,SSLContext sslContext) throws MalformedURLException, IOException{
		Response response = FastHttpClient.post().
				url(url).
				addParams(paramMap).
				build().
				sslContext(sslContext).
				execute();
		return response.body().string();
	}
	//
	public static String httpsPostWithBody(String url,String body) throws MalformedURLException, IOException{
		return httpsPostWithBody(url, body, null);
	}
	//
	public static String httpsPostWithBody(String url,String body,SSLContext sslContext) throws MalformedURLException, IOException{
		Response response = FastHttpClient.post().
				url(url).
				body(body).
				build().
				sslContext(sslContext).
				execute();
		return response.body().string();
	}
    //
    public static void main(String[] args) throws Exception {
		System.out.println(URLUtil.httpGet("http://sz.bendibao.com/news/2016923/781534.htm"));
		System.out.println(URLUtil.httpsGet("https://kyfw.12306.cn/otn/"));
		System.out.println(URLUtil.httpsPost("https://skydu.cn"));
	}
}
*/
