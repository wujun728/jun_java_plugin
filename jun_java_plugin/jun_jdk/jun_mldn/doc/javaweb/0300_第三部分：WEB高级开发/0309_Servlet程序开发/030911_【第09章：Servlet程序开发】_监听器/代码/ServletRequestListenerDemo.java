package org.lxh.listenerdemo ;
import javax.servlet.* ;
public class ServletRequestListenerDemo implements ServletRequestListener {
	public void requestInitialized(ServletRequestEvent sre){
		System.out.println("** request≥ı ºªØ°£http://" + 
			sre.getServletRequest().getRemoteAddr() + 
			sre.getServletContext().getContextPath()) ;
	}
	public void requestDestroyed(ServletRequestEvent sre){
		System.out.println("** requestœ˙ªŸ°£http://" + 
			sre.getServletRequest().getRemoteAddr() + 
			sre.getServletContext().getContextPath()) ;
	}
}
/*
	<listener>
		<listener-class>
			org.lxh.listenerdemo.ServletRequestListenerDemo
		</listener-class>
	</listener>
*/