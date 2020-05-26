package com.java1234.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author caofeng
 *
 */
public class LifeServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("service");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("service");
	}

	@Override
	public void destroy() {
		System.out.println("servletœ˙ªŸ");
	}

	@Override
	public void init() throws ServletException {
		System.out.println("servlet≥ı ºªØ");
	}
	
	

	
}
