package com.jun.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * ����Session��ٵĶ�����
 *  �����������session��ʱ�򣬴������б?�����Ƴ��׵ĵ�½�û�
 * @author Jie.Yuan
 *
 */
public class SessionListener implements HttpSessionListener{

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//1. ��ȡSession����ServletContext����
		HttpSession session = se.getSession();
		ServletContext sc = session.getServletContext();
		
		//2. ��ȡSession�д洢�ĵ�ǰ��½�û�
		Object obj = session.getAttribute("loginInfo");//?
		
		//3. ��ȡServletContext�д洢�������û��б?��
//		List<Admin> list = (List<Admin>) sc.getAttribute("onlineList");
		// ���ж�
		if (obj != null){
			//4. �ѡ���ǰ��½�û����������б?�����Ƴ�
//			list.remove(obj);
		}
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
	}

}
