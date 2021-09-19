package com.tjgd.filter;

import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.*;
@WebFilter(value = { "/*" })
public class CharacterEncodingFilter implements Filter {
	protected String encoding = null;
	//-----初始化方法----------------------------------------
	public void init(FilterConfig config) throws ServletException {
		this.encoding = "utf-8";
	}
	//-----过滤器方法----------------------------------------
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		response.setContentType("text/html; charset=" + encoding);
		System.out.println("调用过滤器，编码格式为" + encoding);
		chain.doFilter(request, response);
	}
    //-----销毁方法------------------------------------------
	public void destroy() {
		this.encoding = null;
	}
}
