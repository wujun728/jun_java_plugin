package com.jun.plugin.servlet.test2.listener;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class SessionAttributeListener implements HttpSessionAttributeListener{

	public void attributeAdded(HttpSessionBindingEvent httpSessionBindingEvent) {
		// TODO Auto-generated method stub
		System.out.println("��ӵ���������"+httpSessionBindingEvent.getName()+",����ֵ��"+httpSessionBindingEvent.getValue());
	}

	public void attributeRemoved(HttpSessionBindingEvent httpSessionBindingEvent) {
		// TODO Auto-generated method stub
		System.out.println("ɾ������������"+httpSessionBindingEvent.getName()+",����ֵ��"+httpSessionBindingEvent.getValue());
	}

	public void attributeReplaced(HttpSessionBindingEvent httpSessionBindingEvent) {
		// TODO Auto-generated method stub
		
	}

}
