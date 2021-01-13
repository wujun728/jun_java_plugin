package com.jun.web.listener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ExecuteListener implements ServletRequestListener,ServletContextListener,HttpSessionListener {
	public ExecuteListener(){
		System.out.println("�ղι���");
		System.out.println(this.hashCode());
	}
	private int counter;
	private Timer timer = new Timer();
	private static Map<String,String> map = new HashMap<String,String>();
	static{
		map.put("127.0.0.1","����");
		map.put("192.168.10.96","����");
		map.put("192.168.10.113","��ɽ");
		map.put("192.168.10.124","�麣");
	}
	
	public void requestInitialized(ServletRequestEvent sre) {
		synchronized (this) {
			this.counter++;
		}
		HttpServletRequest request = (HttpServletRequest) sre.getServletRequest();
		String ip = request.getRemoteAddr();
		String address = map.get(ip);
		if(address==null){
			address = "����";
		}
		request.setAttribute("ip",ip);
		request.setAttribute("address",address);
		request.setAttribute("counter",this.counter);
	}
	public void requestDestroyed(ServletRequestEvent sre) {
	}
	
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		System.out.println(session.getId());
		System.out.println("sessionCreated()" + new Date().toLocaleString());
	}
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		System.out.println(session.getId());
		System.out.println("sessionDestroyed()");
		System.out.println("sessionCreated()" + new Date().toLocaleString());
	}
	
	
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println(this.hashCode());
		try {
//			SystemDao systemDao = new SystemDao();
//			systemDao.createTable("systemInit");
//			timer.schedule(new SystemTask(),0,5*1000);
		} catch (Exception e) {
		}
	}
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println(this.hashCode());
		try {
//			SystemDao systemDao = new SystemDao();
//			systemDao.dropTable("systemInit");
			//��ֹ��ʱ��
			timer.cancel();
		} catch (Exception e) {
		}
	}
	
}


class SystemTask extends TimerTask{
	public void run() {
		try {
//			SystemDao systemDao = new SystemDao();
//			systemDao.init("systemInit",UUID.randomUUID().toString());
		} catch (Exception e) {
		}
	}
}
