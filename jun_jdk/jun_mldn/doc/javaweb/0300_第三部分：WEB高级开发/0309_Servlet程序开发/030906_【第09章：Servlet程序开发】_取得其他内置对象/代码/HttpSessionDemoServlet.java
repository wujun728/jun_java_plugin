package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class HttpSessionDemoServlet extends HttpServlet {
	public void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		HttpSession ses = req.getSession() ;
		System.out.println("SESSION ID --> " + ses.getId()) ;
		ses.setAttribute("username","李兴华") ;	 // 设置session属性
		System.out.println("username属性内容：" + ses.getAttribute("username")) ;
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
}
/*
	<servlet>
		<servlet-name>sessiondemo</servlet-name>
		<servlet-class>
			org.lxh.servletdemo.HttpSessionDemoServlet
		</servlet-class>
	</servlet>
	<servlet-mapping>
		<servlet-name>sessiondemo</servlet-name>
		<url-pattern>/HttpSessionDemoServlet</url-pattern>
	</servlet-mapping>
*/