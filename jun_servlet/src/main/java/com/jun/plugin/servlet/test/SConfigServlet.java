package com.itheima;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SConfigServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletConfig config = this.getServletConfig();
		//--获取当前Servlet 在web.xml中配置的名称
//		String sName = config.getServletName();
//		System.out.println(sName);
		//--获取当前Servlet中配置的初始化参数
//		String value = config.getInitParameter("name2");
//		System.out.println(value);
		
//		Enumeration enumration = config.getInitParameterNames();
//		while(enumration.hasMoreElements()){
//			String name = (String) enumration.nextElement();
//			String value = config.getInitParameter(name);
//			System.out.println(name+":"+value);
//		}
		//--获取ServletContext对象
		ServletContext context = config.getServletContext();
		this.getServletContext();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
