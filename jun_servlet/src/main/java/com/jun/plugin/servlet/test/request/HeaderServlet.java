package com.itheima.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * request获取请求头信息
 */
public class HeaderServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		String value = request.getHeader("Host");
//		System.out.println(value);
		
//		Enumeration<String> enumeration = request.getHeaderNames();
//		while(enumeration.hasMoreElements()){
//			String name = enumeration.nextElement();
//			String value = request.getHeader(name);
//			System.out.println(name+":"+value);
//		}
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
