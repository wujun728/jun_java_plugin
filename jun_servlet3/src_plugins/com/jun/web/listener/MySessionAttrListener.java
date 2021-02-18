package com.jun.web.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * ����session�������Եı仯
 * @author Jie.Yuan
 *
 */
public class MySessionAttrListener implements HttpSessionAttributeListener {

	// �������
	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// �Ȼ�ȡsession����
		HttpSession session = se.getSession();
		// ��ȡ��ӵ�����
		Object obj = session.getAttribute("userName");
		// ����
		System.out.println("��ӵ����ԣ�" + obj);
	}

	// �����Ƴ�
	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		System.out.println("�����Ƴ�");
	}

	// ���Ա��滻
	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// ��ȡsesison����
		HttpSession session = se.getSession();
		
		// ��ȡ�滻ǰ��ֵ
		Object old = se.getValue();
		System.out.println("ԭ����ֵ��" + old);
		
		// ��ȡ��ֵ
		Object obj_new = session.getAttribute("userName");
		System.out.println("��ֵ��" + obj_new);
		
	}

}
