package com.jun.plugin.ajax.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class RegisterServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		
		String username = request.getParameter("username").trim();
		
		System.out.println("username = "+username);
		
		//模拟查询数据库
		if(username==null||username.equals("")){
			out.println("请输入用户名！");
		}else if("sa".equals(username)){
			out.println("该用户名已存在！");
		}else{
			out.println("该用户名可以注册");
		}
	}
}
