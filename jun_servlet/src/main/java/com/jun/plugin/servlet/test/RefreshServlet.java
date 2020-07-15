package com.jun.plugin.servlet.test;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RefreshServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		response.getWriter().write(new Date().toLocaleString());
//		response.setHeader("Refresh", "1");
		
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().write("��ϲ��ע��ɹ�!3���ص���ҳ.....");
		response.setHeader("refresh", "3;url=/Day04/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
