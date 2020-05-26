package com.java1234.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter{

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)servletRequest;
		HttpSession session=request.getSession();
		Object o=session.getAttribute("currentUser");
		String path=request.getServletPath();
		if(o==null&&path.indexOf("login")<0){
			request.getRequestDispatcher("login.jsp").forward(servletRequest, servletResponse);
		}else{
			filterChain.doFilter(servletRequest, servletResponse);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
