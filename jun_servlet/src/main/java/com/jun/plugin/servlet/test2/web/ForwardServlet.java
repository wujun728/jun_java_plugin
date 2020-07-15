package com.jun.plugin.servlet.test2.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ForwardServlet extends HttpServlet{

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
		request.setAttribute("requestKey", "requestֵ");
		HttpSession session=request.getSession();  // ��ȡsession
		session.setAttribute("sessionKey", "sessionֵ");
		ServletContext application=this.getServletContext(); // ��ȡapplication
		application.setAttribute("applicationKey", "applicationֵ");
		RequestDispatcher rd=request.getRequestDispatcher("target.jsp");
		rd.forward(request, response); // ��������ת/ת��
	}
	
	

	
}
