package com.itheima.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Demo5 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//response.setContentType("text/html;charset=utf-8");
		//response.getWriter().write("from demo5....");
		//response.getWriter().flush();
		System.out.println("before 5");
		response.getWriter().write("out from 5 bef");
		
		request.getRequestDispatcher("/servlet/Demo6").forward(request, response);
		
		System.out.println("after 5");
		response.getWriter().write("out from 5 aft");
		
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
