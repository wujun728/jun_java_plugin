package com.jun.web.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class CharFilter implements Filter {
	// 声明编码的成员变量
	private String encoding;

	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("bm");
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);//对get没有效
		response.setContentType("text/html;charset=" + encoding);
		// 判断是否需要包装
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getMethod().equals("GET")) {
			request = new MyRequest(req);
		}
		// 声明包装类的实例
		// 放行
		chain.doFilter(request, response);
	}
	public void destroy() {
	}
}

// 声明包装类
class MyRequest extends HttpServletRequestWrapper {
	private String[] ss;
	
	public MyRequest(HttpServletRequest request) {
		//读取数据库，将读取的数据放到缓存
		super(request);
		ss=new String[]{"SB","江泽民","小胡"};
	}

	// 增强getParamter
	@Override
	public String getParameter(String name) {
		String val = super.getParameter(name);
		try {
			val = new String(val.getBytes("ISO-8859-1"),
					super.getCharacterEncoding());
			for(String s:ss){
				val = val.replace(s, "****");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return val;
	}
}
