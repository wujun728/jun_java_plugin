package com.jun.web;


import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddCustomerServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// ��������
		request.setCharacterEncoding("utf-8");

		String[] preference = request.getParameterValues("preference");
		// ���磺���� ���� ------ [����,����]
		String value = Arrays.toString(preference);
		value = value.substring(1, value.length() - 1);
//		customer.setPreference(value);
//		CustomerService service = new CustomerService();
//		service.addCustomer(customer);

		// ��תindex.jsp
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
