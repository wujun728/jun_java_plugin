package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class ServletContextDemoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		ServletContext app = super.getServletContext() ;
		System.out.println("ÕæÊµÂ·¾¶£º" + app.getRealPath("/")) ;
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
}
/*
	<servlet>
		<servlet-name>applicationdemo</servlet-name>
		<servlet-class>
			org.lxh.servletdemo.ServletContextDemoServlet
		</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>applicationdemo</servlet-name>
		<url-pattern>/ServletContextDemoServlet</url-pattern>
	</servlet-mapping>
*/