package com.jun.web.filter;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CacheFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//转换
		HttpServletResponse resp = (HttpServletResponse) response;
		resp.setHeader("expires","-1");
  		resp.setHeader("pragma","no-cache");
  		resp.setHeader("cache-control","no-cache");
		//都放行
		chain.doFilter(request,resp);
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
