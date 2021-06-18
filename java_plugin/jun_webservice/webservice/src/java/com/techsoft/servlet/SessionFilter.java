package com.techsoft.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.alibaba.fastjson.JSONObject;
import com.techsoft.StringConsts;
import com.techsoft.container.DataServer;

public class SessionFilter implements Filter {
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;

		ConfigProperties configProp = DataServer.getInstance().getProperties();
        
		String gOperUserId=configProp.getGOperUserId();
		
		if(gOperUserId!=null && !"".equals(gOperUserId)){
			request.setAttribute("g_user_id", new Long(gOperUserId));
		}else{		
			Cookie[] cookies = req.getCookies();
			String url = null;
			if (cookies != null) {
				for (final Cookie oItem : cookies) {
					final String sName = oItem.getName();
					if (sName.equalsIgnoreCase("authToken")) {
						url = DataServer.getInstance().getProperties()
								.getSessionURL();
						if ((url != null) && (!url.equalsIgnoreCase(""))) {
							url = url + "?authToken=" + oItem.getValue();
							HttpClient client = new HttpClient();
							JSONObject result = null;
							HttpMethod httpGet = new PostMethod(url);
							try {
								client.executeMethod(httpGet);
								if ((httpGet.getStatusLine() != null)
										&& (httpGet.getStatusLine().getStatusCode() == 200)) {
									BufferedReader tBufferedReader = new BufferedReader(
											new InputStreamReader(httpGet
													.getResponseBodyAsStream()));
									StringBuffer tStringBuffer = new StringBuffer();
									String sTempOneLine = "";
									try {
										while ((sTempOneLine = tBufferedReader
												.readLine()) != null) {
											tStringBuffer.append(sTempOneLine);
										}
	
										String s = URLDecoder
												.decode(tStringBuffer.toString(),
														System.getProperty(StringConsts.jvmcharset));
										result = JSONObject.parseObject(s);
	
										JSONObject json = result
												.getJSONObject("valueObject");
										if (json != null) {
											String temp = json
													.getString("userInfo");
											temp = temp.replace("\\\"", "\"");
											json = (JSONObject) JSONObject
													.parse(temp);
											// json =
											// json.getJSONObject("userInfo");
											if (json != null) {
												json = json.getJSONObject("user");
												if (json != null) {
													Long user_id = Long
															.valueOf(json
																	.getString("fOperUserId"));
													request.setAttribute(
															"g_user_id", user_id);
												}
											}
										}
	
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} catch (HttpException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}

		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
