package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class ServerRedirectDemo extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		req.getSession().setAttribute("name","李兴华") ;
		req.setAttribute("info","MLDNJAVA") ;
		RequestDispatcher rd = req.getRequestDispatcher("get_info.jsp") ;	// 准备好了跳转操作
		rd.forward(req,resp) ;	// 完成跳转
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
}
/*
	<servlet>
		<servlet-name>server</servlet-name>
		<servlet-class>
			org.lxh.servletdemo.ServerRedirectDemo
		</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>server</servlet-name>
		<url-pattern>/forward/ServerRedirectDemo</url-pattern>
	</servlet-mapping>
*/