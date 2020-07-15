package com.itheima.request;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ParamServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//request.setCharacterEncoding("utf-8");//
		
		String username = request.getParameter("username");
		username = new String(username.getBytes("iso8859-1"),"utf-8");
		System.out.println(username);
		
//		Enumeration<String> enumeration = request.getParameterNames();
//		while(enumeration.hasMoreElements()){
//			String name = enumeration.nextElement();
//			String value = request.getParameter(name);
//			System.out.println(name+":"+value);
//		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
