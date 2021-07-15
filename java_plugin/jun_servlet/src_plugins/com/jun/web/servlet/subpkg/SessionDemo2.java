package com.jun.web.servlet.subpkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * ��session�������ȡ���Ự���
 * @author APPle
 *
 */
public class SessionDemo2 extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//1.�õ�session����
		HttpSession session = request.getSession(false);
		
		if(session==null){
			System.out.println("û���ҵ���Ӧ��sessino����");
			return;
		}
		
		/**
		 * �õ�session���
		 */
		System.out.println("id="+session.getId());
		
		//2.ȡ�����
		String name = (String)session.getAttribute("name");
		System.out.println("name="+name);
	}

}
