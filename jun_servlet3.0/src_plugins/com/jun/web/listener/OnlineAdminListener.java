package com.jun.web.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * ��ʼ�������б?�ϼ�����
 * 
 * @author Jie.Yuan
 * 
 */
public class OnlineAdminListener implements ServletContextListener {

	//1. ServletContext���󴴽�
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// �������ϣ���������û�
		// ÿ�ε��û���½�󣬾�����������������ӵ�ǰ��½��
//		List<Admin> onlineList = new ArrayList<Admin>();
		// ����ServletContext��
//		sce.getServletContext().setAttribute("onlineList", onlineList);
	}

	//2. ServletContext�������
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// ��ȡServletContext
		ServletContext sc = sce.getServletContext();
		// ��ȡ�����б�
		Object obj = sc.getAttribute("onlineList");
		// �Ƴ������б?��
		if (obj != null) {
			sc.removeAttribute("onlineList");
		}
	}

}
