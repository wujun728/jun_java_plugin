package com.jun.plugin.bizservice.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.plugin.bizservice.util.ResponseUtil;
import com.jun.plugin.webservlet.BaseServlet;

@WebServlet(name="actionServlet",urlPatterns="/action", asyncSupported = true)
@WebInitParam(name="test", value="123") 
public class ActionServlet extends BaseServlet {

	private static final long serialVersionUID = -2166745054117904738L;
	
	/**
	 * 查找
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * http://localhost:8080/jun_webservlet/action?method=gopage
	 */
	public void rRedirect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");  //获取findname参数的值
		System.out.println("username:"+username);    //打印username
		request.setAttribute("session_username",username);
 
		if("admin".equals(username)){   //如果username是"admin"  就将它保存到request域中
			session.setAttribute("request_username",username);
		}
		request.getRequestDispatcher("/index.jsp").forward(request,response);  //转发到index.jsp页面
		//response.sendRedirect(rootPath+"/index.jsp");
	}
	
	/**
	 * 查找
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 * http://localhost:8080/jun_webservlet/action?method=add
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //执行service层代码
    	System.out.println(" action add ");
		ResponseUtil.write(response, "add");
    }
    
}