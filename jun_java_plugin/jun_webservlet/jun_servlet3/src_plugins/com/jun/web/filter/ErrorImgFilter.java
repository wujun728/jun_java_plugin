package com.jun.web.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * 如果上传的图片或者文件丢失的情况下，返回一个默认的图片，用于提示用户
 * 
 * @author zhangdaihao
 * 
 */

//@WebFilter(filterName="AdminLoginFilter" ,urlPatterns={"/jsp/img/*"},
//initParams={ @WebInitParam(name="name",value="xiazdong"),  @WebInitParam(name="age",value="20") },asyncSupported=true ) 
public class ErrorImgFilter extends HttpServlet implements Filter {

	private static final Logger logger = Logger.getLogger(ErrorImgFilter.class);

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String realPath = req.getSession().getServletContext().getRealPath("/");
		String contextPath = req.getContextPath();
		String requestURI = req.getRequestURI();

		String fileUrl = realPath + requestURI.substring(contextPath.length());

		File f = new File(fileUrl);
		if (!f.exists()) {
			request.getRequestDispatcher("/css/images/blue_face/bluefaces_35.png").forward(request, response);
			return;
		}

		chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {

	}

}
