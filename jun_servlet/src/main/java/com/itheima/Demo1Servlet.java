package com.itheima;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.SingleThreadModel;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Demo1Servlet extends HttpServlet {
	int i = 0;
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			i++;
			try {
				Thread.sleep(4*1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			response.getWriter().write(i+"");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
