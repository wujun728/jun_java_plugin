package org.lxh.servletdemo ;
import java.io.* ;
import javax.servlet.* ;
import javax.servlet.http.* ;
public class LifeCycleServlet extends HttpServlet{
	public void init() throws ServletException{
		System.out.println("** 1、Servlet初始化 --> init()") ;
	}
	public void doGet(HttpServletRequest req,HttpServletResponse resp)
              throws ServletException,IOException{
		System.out.println("** 2、Servlet服务 --> doGet()、doPost()") ;
	}
	public void doPost(HttpServletRequest req,HttpServletResponse resp)
              throws ServletException,IOException{
		this.doGet(req,resp) ;
	}
	public void destroy(){
		System.out.println("** 3、Servlet销毁 --> destory()") ;
		try{
			Thread.sleep(3000) ;
		}catch(Exception e){}
	}/*
	public void service(ServletRequest req,
                             ServletResponse res)
                      throws ServletException,
                             IOException{
		System.out.println("************ 服务 **************") ;
	}
	*/
}