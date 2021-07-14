package com.jun.plugin.json;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GetAjaxNameServlet extends HttpServlet{

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
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		System.out.println("name="+name);
		System.out.println("age="+age);
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out=response.getWriter();
		out.println("ajax提示信息");
		out.flush();
		out.close();
	}

	
	
}
