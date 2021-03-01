package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class InitParamServlet extends HttpServlet {
	private String initParam = null ;	// 用于保存初始化参数
	public void init() throws ServletException{
		System.out.println("*****************") ;
	}

	public void init(ServletConfig config) throws ServletException{
		System.out.println("#######################") ;
		this.initParam = config.getInitParameter("ref") ;	// 接收的初始化参数名称暂时为ref
	}

	public void doGet(HttpServletRequest req,
                     HttpServletResponse resp)
              throws ServletException,
                     IOException{
		System.out.println("** 初始化参数：" + this.initParam) ;
	}
	public void doPost(HttpServletRequest req,
                     HttpServletResponse resp)
              throws ServletException,
                     IOException{
		this.doGet(req,resp) ;
	
	}



}
/*
	<servlet>
		<servlet-name>initparam</servlet-name>
		<servlet-class>org.lxh.servletdemo.InitParamServlet</servlet-class>
		<init-param>
			<param-name>ref</param-name>
			<param-value>www.MLDNJAVA.cn</param-value>
		</init-param>
	</servlet>
	<servlet-mapping>
		<servlet-name>initparam</servlet-name>
		<url-pattern>/InitParamServlet</url-pattern>
	</servlet-mapping>
*/