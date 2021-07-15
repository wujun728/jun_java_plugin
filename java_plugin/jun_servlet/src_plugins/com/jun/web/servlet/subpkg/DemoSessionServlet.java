package com.jun.web.servlet.subpkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DemoSessionServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		//JSESSIONID=DFE75A1E9CCAA1591F900FA3B1AEE9F3 tomcat自动添加cookie，会话级
		//获得session
		HttpSession session = request.getSession(); //没有创建，有返回
		
		System.out.println(session.isNew());
		
		
		//持久化
		Cookie cookie = new Cookie("JSESSIONID",session.getId());
		//设置有效时间
		cookie.setMaxAge(60*30);
		//通知浏览器
		response.addCookie(cookie);
		
		
		

	}

}
