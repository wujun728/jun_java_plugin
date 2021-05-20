package com.jun.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


//@WebFilter(filterName = "CountFilter", urlPatterns = { "/*.jsp" }, initParams = {
//		@WebInitParam(name = "name", value = "jun") }, asyncSupported = true)
public class CountFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	// *.jsp
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		ServletContext app = req.getSession().getServletContext();
		Integer count = (Integer) app.getAttribute("count");
		if(count==null){
			count=1;
		}else{
			count++;
		}
		app.setAttribute("count",count);
		chain.doFilter(request, response);
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
