package com.jun.web.listener;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;

//import com.sun.faces.config.WebConfiguration;

public class MyContextAttiListener implements ServletContextAttributeListener {
	public void attributeAdded(ServletContextAttributeEvent e) {
		System.err.println("有人添加新的属性："+e.getName()+","+e.getValue());
		if(e.getName().equals("com.sun.faces.config.WebConfiguration")){
//			WebConfiguration wc = (WebConfiguration) e.getValue();
//			System.err.println(wc.getServletContextName());
		}
		
	}
	public void attributeRemoved(ServletContextAttributeEvent e) {
	}

	public void attributeReplaced(ServletContextAttributeEvent e) {
	}
}