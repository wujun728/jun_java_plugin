package com.jun.web.servlet.subpkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * ����Ự��ݵ�session�����
 * @author APPle
 *
 */
public class SessionDemo1 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.����session����
		HttpSession session = request.getSession();
		
		/**
		 * �õ�session���
		 */
		System.out.println("id="+session.getId());
		
		/**
		 * �޸�session����Чʱ��
		 */
		//session.setMaxInactiveInterval(20);
		
		/**
		 * �ֶ�����һ��Ӳ�̱����cookie�������
		 */
		Cookie c = new Cookie("JSESSIONID",session.getId());
		c.setMaxAge(60*60);
		response.addCookie(c);
		
		
		//2.����Ự���
		session.setAttribute("name", "rose");
	}

}
