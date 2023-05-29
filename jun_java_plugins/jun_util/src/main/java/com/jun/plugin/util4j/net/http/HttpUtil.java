package com.jun.plugin.util4j.net.http;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import org.apache.commons.lang.StringUtils;

import com.jun.plugin.util4j.bytesStream.InputStreamUtils;

import io.netty.util.CharsetUtil;

public class HttpUtil {

	private int readTimeOut=3000;
	private int connectTimeOut=3000;
	
	public HttpUtil() {

	}
	
	public HttpUtil(int readTimeOut, int connectTimeOut) {
		super();
		this.readTimeOut = readTimeOut;
		this.connectTimeOut = connectTimeOut;
	}

	public int getReadTimeOut() {
		return readTimeOut;
	}

	public void setReadTimeOut(int readTimeOut) {
		this.readTimeOut = readTimeOut;
	}

	public long getConnectTimeOut() {
		return connectTimeOut;
	}

	public void setConnectTimeOut(int connectTimeOut) {
		this.connectTimeOut = connectTimeOut;
	}

	public HttpURLConnection buildSSLConn(String url)throws Exception {
		SSLContext sc = SSLContext.getInstance("SSL");  
        sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());  
        URL console = new URL(url);  
        HttpsURLConnection conn = (HttpsURLConnection) console.openConnection();  
        conn.setSSLSocketFactory(sc.getSocketFactory());  
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());  
		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);
		return conn;
	}
	
	public HttpURLConnection buildConn(String url)throws Exception {
		HttpURLConnection conn=(HttpURLConnection) new URL(url).openConnection();
		conn.setConnectTimeout(connectTimeOut);
		conn.setReadTimeout(readTimeOut);
		return conn;
	}
	
	public byte[] httpGet(String url) throws Exception
	{
		HttpURLConnection conn=buildConn(url);
		try {
			return InputStreamUtils.getBytes(conn.getInputStream());
		} finally {
			conn.getInputStream().close();
			conn.disconnect();
		}
	}
	
	public byte[] httpPost(String url,Map<String,String> args) throws Exception
	{
		List<String> list=new ArrayList<String>();
		for(Entry<String, String> entry:args.entrySet())
		{
			list.add(entry.getKey()+"="+entry.getValue());
		}
		String content=StringUtils.join(list, "&");
		return httpPost(url,content.getBytes("utf-8"));
	}
	
	public byte[] httpsPost(String url,Map<String,String> args) throws Exception
	{
		List<String> list=new ArrayList<String>();
		for(Entry<String, String> entry:args.entrySet())
		{
			list.add(entry.getKey()+"="+entry.getValue());
		}
		String content=StringUtils.join(list, "&");
		return httpsPost(url,content.getBytes("utf-8"));
	}
	
	public byte[] httpsPost(String url,byte[] data) throws Exception
	{
		HttpURLConnection conn=buildSSLConn(url);
		try {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.getOutputStream().write(data);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			return InputStreamUtils.getBytes(conn.getInputStream());
		} finally {
			conn.getInputStream().close();
			conn.disconnect();
		}
	}
	
	public byte[] httpPost(String url,byte[] data) throws Exception
	{
		HttpURLConnection conn=buildConn(url);
		try {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.getOutputStream().write(data);
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			return InputStreamUtils.getBytes(conn.getInputStream());
		} finally {
			conn.getInputStream().close();
			conn.disconnect();
		}
	}
	
	public byte[] httpPostJson(String url,String json) throws Exception
	{
		HttpURLConnection conn=buildConn(url);
		try {
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Type","application/json");
			conn.getOutputStream().write(json.getBytes(CharsetUtil.UTF_8));
			conn.getOutputStream().flush();
			conn.getOutputStream().close();
			return InputStreamUtils.getBytes(conn.getInputStream());
		} finally {
			conn.getInputStream().close();
			conn.disconnect();
		}
	}
}
