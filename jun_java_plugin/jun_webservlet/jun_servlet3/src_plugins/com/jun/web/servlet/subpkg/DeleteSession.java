package com.jun.web.servlet.subpkg;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 * ���session����
 * @author APPle
 *
 */
public class DeleteSession extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session!=null){
			session.invalidate();//�ֶ����
		}
		System.out.println("��ٳɹ�");
		
	}

}
