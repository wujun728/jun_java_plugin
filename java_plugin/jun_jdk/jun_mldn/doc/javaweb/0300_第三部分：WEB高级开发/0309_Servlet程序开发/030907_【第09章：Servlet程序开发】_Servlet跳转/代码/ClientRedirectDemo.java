package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class ClientRedirectDemo extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		req.getSession().setAttribute("name","ÀîÐË»ª") ;
		req.setAttribute("info","MLDNJAVA") ;
		resp.sendRedirect("get_info.jsp") ;
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
}
/*
	<servlet>
		<servlet-name>client</servlet-name>
		<servlet-class>
			org.lxh.servletdemo.ClientRedirectDemo
		</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>client</servlet-name>
		<url-pattern>/forward/ClientRedirectDemo</url-pattern>
	</servlet-mapping>
*/