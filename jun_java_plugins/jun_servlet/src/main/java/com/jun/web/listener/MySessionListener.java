package com.jun.web.listener;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * 以下实现在线人数的统计
 */
public class MySessionListener implements HttpSessionListener {
	private Integer online=1;
	//request.getSession();
	public void sessionCreated(HttpSessionEvent se) {
		System.err.println("有人访问本网点了");
		HttpSession hs1 = (HttpSession) se.getSource();
		HttpSession hs2 = se.getSession();
		System.err.println("hs1:"+hs1.getId()+","+hs2.getId()+",ip:");
		//获取整个域的对象
		ServletContext sc= se.getSession().getServletContext();
		sc.setAttribute("online",online++);
		//将所有session放到servletContext
		//先从application获取所有已经维护的sesison
		List<HttpSession> list = (List<HttpSession>) sc.getAttribute("sessions");
		if(list==null){//第一个访问的人
			list = new ArrayList<HttpSession>();
			sc.setAttribute("sessions", list);
		}
		list.add(hs2);
	}
	//过期(30),s.invalidate();
	public void sessionDestroyed(HttpSessionEvent se) {
		System.err.println("有人退出了..."+se.getSession().getId());
		ServletContext sc= se.getSession().getServletContext();
		sc.setAttribute("online",online--);
	}
}
