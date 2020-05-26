package com.java1234.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RedirectServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("requestKey", "request值");
		HttpSession session=request.getSession();  // 获取session
		session.setAttribute("sessionKey", "session值");
		ServletContext application=this.getServletContext(); // 获取application
		application.setAttribute("applicationKey", "application值");
		response.sendRedirect("target.jsp"); // 客户端跳转/重定向
	}
	
	

	
}
