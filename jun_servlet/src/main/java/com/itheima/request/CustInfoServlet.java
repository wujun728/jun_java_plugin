package com.itheima.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustInfoServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.获取客户端请求的完整URL
		String url = request.getRequestURL().toString();
		System.out.println(url);
		//2.获取客户端请求的资源部分的名称
		String uri = request.getRequestURI();
		System.out.println(uri);
		//3.获取请求行中参数部分
		String qStr = request.getQueryString();
		System.out.println(qStr);
		//4.获取请求客户端的ip地址
		String ip = request.getRemoteAddr();
		System.out.println(ip);
		//5.获取客户机的请求方式
		String method = request.getMethod();
		System.out.println(method);
		//6.获取当前web应用的名称
		String name = request.getContextPath();
		System.out.println(name);
		
		response.sendRedirect(request.getContextPath()+"/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
