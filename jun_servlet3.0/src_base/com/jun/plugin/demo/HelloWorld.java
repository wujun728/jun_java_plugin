package com.jun.plugin.demo;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class HelloWorld implements Servlet {

	public void init(ServletConfig arg0) throws ServletException {
		//第一个输出的内容，输出了1次
		System.out.println("init"); 
	}

	public void service(ServletRequest arg0, ServletResponse arg1)
			throws ServletException, IOException {
		//输出n次
		System.out.println("service");
	}

	public void destroy() {
		//服务器关闭时，输出1次
		System.out.println("destroy");
	}

	public ServletConfig getServletConfig() {
		return null;
	}

	public String getServletInfo() {
		return null;
	}

}
