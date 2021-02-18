package com.java1234.web;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AjaxServlet extends HttpServlet{

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
		request.setCharacterEncoding("utf-8");
		String action=request.getParameter("action");
		if("load".equals(action)){
			this.load(request, response);
		}else if("post".equals(action)){
			this.post(request, response);
		}
	}
	
	private void load(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String name=request.getParameter("name");
		String age=request.getParameter("age");
		System.out.println("name:"+name);
		System.out.println("age:"+age);
		PrintWriter out=response.getWriter();
		out.println("jquery Ajax 大爷你好");
		out.flush();
		out.close();
	}
	
	private void post(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		String id=request.getParameter("id");
		System.out.println("id:"+id);
		PrintWriter out=response.getWriter();
		out.println("{name:'张三',age:14}");
		out.flush();
		out.close();
	}

}
