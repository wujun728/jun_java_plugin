package com.jun.web.servlet.subpkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteCookie extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * ���� ɾ��cookie
		 */
		Cookie cookie = new Cookie("name","xxxx");
		cookie.setMaxAge(0);//ɾ��ͬ���cookie
		response.addCookie(cookie);
		System.out.println("ɾ��ɹ�");
		
	}

}
