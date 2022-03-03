package com.jun.admin.servlet;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class EncodingFilter implements Filter { 
    	Pattern pattern=Pattern.compile("[\\u4e00-\\u9fa5]");  
	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String queryStr = req.getQueryString();
		if(queryStr != null){
			queryStr = new String(queryStr.getBytes("ISO-8859-1"),"UTF-8");
			Matcher matcher = pattern.matcher(queryStr);
			if(matcher.find()){
				request.setCharacterEncoding("UTF-8");
			}else{
				request.setCharacterEncoding("UTF-8");
			}
		}else{
			request.setCharacterEncoding("UTF-8");
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {}
}