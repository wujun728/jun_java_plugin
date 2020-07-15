package com.jun.plugin.servlet.test;

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
		//--��ȡ��ǰServlet ��web.xml�����õ�����
//		String sName = config.getServletName();
//		System.out.println(sName);
		//--��ȡ��ǰServlet�����õĳ�ʼ������
//		String value = config.getInitParameter("name2");
//		System.out.println(value);
		
//		Enumeration enumration = config.getInitParameterNames();
//		while(enumration.hasMoreElements()){
//			String name = (String) enumration.nextElement();
//			String value = config.getInitParameter(name);
//			System.out.println(name+":"+value);
//		}
		//--��ȡServletContext����
		ServletContext context = config.getServletContext();
		this.getServletContext();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
