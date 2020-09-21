/*   
 * Project: OLWEB 433
 * FileName: HttpClientUtils.java
 * Company: Chengdu osmp Technology Co.,Ltd
 * version: V1.0
 */
package com.osmp.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.IOUtils;

/**
 * Description: httpClient
 * 
 * @author: wangkaiping
 * @date: 2015年4月16日 下午2:00:39
 */
public class HttpClientUtils {

	public static void get(HttpServletRequest request, HttpServletResponse response, String targetUrl)
			throws IOException {
		URL url = new URL(targetUrl);
		BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
		String line;
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		while ((line = in.readLine()) != null) {
			out.println(line);
		}
		out.flush();
		in.close();
	}

	/**
	 * 使用POST提交到目标服务器。
	 * 
	 * @param request
	 * @param response
	 * @param targetUrl
	 * @throws IOException
	 */
	public static void post(HttpServletRequest request, HttpServletResponse response, String targetUrl)
			throws IOException {
		URL url = new URL(targetUrl);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// 发送POST请求必须设置如下两行
		conn.setDoOutput(true);
		conn.setDoInput(true);
		conn.setRequestMethod("POST");
		// 可以拷贝客户端的head信息作为请求的head参数
		// conn.setRequestProperty("Charsert", "UTF-8");
		conn.setRequestProperty("Content-Type", "application/json");

		// 直接把客户端的BODY传给目标服务器
		OutputStream send = conn.getOutputStream();
		InputStream body = request.getInputStream();
		IOUtils.copy(body, send);
		send.flush();
		send.close();
		body.close();

		// 获取返回值
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-type", "text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		String line;
		while ((line = in.readLine()) != null) {
			out.println(line);
		}
		out.flush();
	}

	@SuppressWarnings("rawtypes")
	public static String getRequestURL(HttpServletRequest request) {
		if (request == null) {
			return "";
		}
		String url = "";
		url = request.getRequestURL().toString();
		String contPath = request.getContextPath();
		url = url.substring(url.indexOf(contPath), url.length()).replaceAll(contPath, "");
		if ("POST".equals(request.getMethod())) {
			return url;
		}
		java.util.Enumeration names = request.getParameterNames();
		int i = 0;
		if (names != null) {
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				if (i == 0) {
					url = url + "?";
				} else {
					url = url + "&";
				}
				i++;

				String value = request.getParameter(name);
				if (value == null) {
					value = "";
				}
				value = URLEncoder.encode(value);
				url = url + name + "=" + value;
			}
		}

		return url;
	}

	/**
	 * 获取所有参数或者头部信息
	 * 
	 * @param request
	 * @param flag
	 *            boolean true:参数 false:头信息
	 * @return
	 */
	public static Map<String, String> getArgsOrHeaders(HttpServletRequest request, boolean flag) {
		Map<String, String> map = new HashMap<String, String>();
		java.util.Enumeration names = null;
		if (flag) {
			names = request.getParameterNames();
		} else {
			names = request.getHeaderNames();
		}
		if (names != null) {
			while (names.hasMoreElements()) {
				String name = (String) names.nextElement();
				String value = null;
				if (flag) {
					value = request.getParameter(name);
				} else {
					value = request.getHeader(name);
				}
				if (value != null) {
					try {
						String kaikai = new String(value.getBytes("iso8859-1"), "utf-8");
						System.out.println(kaikai);
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					map.put(name, value);
				}
			}
		}
		return map;
	}

	public static void main(String[] args) throws Exception {
		CamelContext context = new DefaultCamelContext();
		context.addRoutes(new RouteBuilder() {
			public void configure() {
				from("file:e:/temp")
						.to("ftp://10.34.38.226:120/?username=wjht&password=123&binary=true&ftpClient.controlEncoding=gb2312");
			}
		});
		context.start();
		Thread.sleep(800 * 1000);
		context.stop();
	}
}
