package com.jun.plugin.demo;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
public class MyListener implements HttpSessionAttributeListener {
	//可以监听到设置usr属性
	public void attributeAdded(HttpSessionBindingEvent se) {
		String key = se.getName();
		if(key.equals("user")){
			//将这个Session缓存到ServletContext<Map<Sid,Session>>
			ServletContext ctx = se.getSession().getServletContext();
			//先读取在servletContext的已经存在的登录的人对象集合
			Map<String,HttpSession> onLogin = 
					(Map<String, HttpSession>) ctx.getAttribute("onLogin");
			//如果还没有登录
			if(onLogin==null){
				onLogin = new HashMap<String, HttpSession>();
				//放到ctx
				ctx.setAttribute("onLogin",onLogin);
			}
			//必须要将用户的sesion放到map
			HttpSession session = se.getSession();
			onLogin.put(session.getId(), session);
		}
	}

	public void attributeRemoved(HttpSessionBindingEvent se) {
		String key= se.getName();
		if(key.equals("user")){
			Map<String,HttpSession> map = 
					(Map<String, HttpSession>) se.getSession()
					.getServletContext().getAttribute("onLogin");
			map.remove(se.getSession().getId());
		}
	}

	public void attributeReplaced(HttpSessionBindingEvent se) {
		
	}
	
}
