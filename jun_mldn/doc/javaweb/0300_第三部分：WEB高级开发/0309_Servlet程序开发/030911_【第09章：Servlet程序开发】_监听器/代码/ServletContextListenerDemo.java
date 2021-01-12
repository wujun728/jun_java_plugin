package org.lxh.listenerdemo ;
import javax.servlet.* ;
public class ServletContextListenerDemo implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event){
		System.out.println("** ÈÝÆ÷³õÊ¼»¯ --> " + event.getServletContext().getContextPath()) ;
	}
	public void contextDestroyed(ServletContextEvent event){
		System.out.println("** ÈÝÆ÷Ïú»Ù --> " + event.getServletContext().getContextPath()) ;
		try{
			Thread.sleep(300) ;
		}catch(Exception e){}
	}
}
/*
	<listener>
		<listener-class>
			org.lxh.listenerdemo.ServletContextListenerDemo
		</listener-class>
	</listener>
*/