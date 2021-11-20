package com.jun.web.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletRequestAttributeEvent;
import javax.servlet.ServletRequestAttributeListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

public class AttributeListener implements ServletContextAttributeListener, HttpSessionAttributeListener, ServletRequestAttributeListener {
	public void attributeAdded(ServletContextAttributeEvent scab) {
		System.out.println(scab.getName() + ":" + scab.getValue());
	}
	public void attributeReplaced(ServletContextAttributeEvent scab) {
		System.out.println(scab.getName() + ":" + scab.getValue());
	}
	public void attributeRemoved(ServletContextAttributeEvent scab) {
		System.out.println(scab.getName() + ":" + scab.getValue());
	}
//	 sessoin.setAttribute("addr","中国北京");
	public void attributeAdded(HttpSessionBindingEvent se) {
		System.out.println(se.getName() + ":" + se.getValue());
	}
	// sessoin.setAttribute("addr",中国北京);
	// sessoin.setAttribute("addr",上海);
	public void attributeReplaced(HttpSessionBindingEvent se) {
		System.out.println(se.getName() + ":" + se.getValue());
	}
	// session.removeAttribute("addr"); - tomcat容器
	public void attributeRemoved(HttpSessionBindingEvent se) {
		System.out.println(se.getName() + ":" + se.getValue());
	}
	public void attributeAdded(ServletRequestAttributeEvent srae) {
		System.out.println(srae.getName() + ":" + srae.getValue());
	}
	public void attributeReplaced(ServletRequestAttributeEvent srae) {
		System.out.println(srae.getName() + ":" + srae.getValue());
	}
	public void attributeRemoved(ServletRequestAttributeEvent srae) {
		System.out.println(srae.getName() + ":" + srae.getValue());
	}
	
	//可以监听到设置usr属性
	@SuppressWarnings("unchecked")
	public void attributeAdded1(HttpSessionBindingEvent se) {
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

	@SuppressWarnings("unchecked")
	public void attributeRemoved1(HttpSessionBindingEvent se) {
		String key= se.getName();
		if(key.equals("user")){
			Map<String,HttpSession> map = (Map<String, HttpSession>) se.getSession().getServletContext().getAttribute("onLogin");
			map.remove(se.getSession().getId());
		}
	}
	
	public void attributeAdded11(HttpSessionBindingEvent se) {
		String name = se.getName();//name="username"
		String value = (String) se.getValue();//value="jack"
		if(name.equals("username")){
			HttpSession session = se.getSession();
			ServletContext context = session.getServletContext();
			Map<String,HttpSession> map = (Map<String, HttpSession>) context.getAttribute("map");
			if(map==null){
				map = new HashMap<String,HttpSession>();
			}
			map.put(value,session);
			context.setAttribute("map",map);
		}
	}
}
