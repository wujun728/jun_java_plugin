package com.jun.web.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class BizFilter implements Filter {
	private FilterConfig conf;
	public void init(FilterConfig filterConfig) throws ServletException {
		this.conf = filterConfig;
		
		String name = conf.getInitParameter("name");
		System.err.println("name is:"+name);
		System.err.println("----------------------------");
	 	Enumeration<String> en= conf.getInitParameterNames();
		while(en.hasMoreElements()){
			String paramName = en.nextElement();
			String val = conf.getInitParameter(paramName);
			System.err.println(paramName+"="+val);
		}
		
	}
	
	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	//***************************************************************************************************************
	//***************************************************************************************************************
	
	public void AdminLoginFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//验证在session中是否存在admin这个key
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("admin")==null){
			String loginPage = conf.getInitParameter("loginPage");
			req.getRequestDispatcher(loginPage).forward(request, response);
		}else{
			chain.doFilter(request, response);
		}
	}
	//***************************************************************************************************************
	//***************************************************************************************************************

//	public void AuthFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		//获取uri
//		HttpServletRequest req = (HttpServletRequest) request;
//		String uri = req.getRequestURI();//Http://localhost:8080/day20/jsps/role.jsp->day20/jsps/role.jsp
//		uri = uri.replace(req.getContextPath(), "");
//		//组成sql
//		String sql = "SELECT COUNT(1)"+
//				     " FROM menus m INNER JOIN rolemenu rm ON m.id=rm.mid"+
//				     " INNER JOIN roles r ON r.id=rm.rid"+
//				     " INNER JOIN roleuser ru ON r.id=ru.rid"+
//				     " WHERE ru.uid=? AND url=?";
//		//取到用户的id
////		User user = (User) req.getSession().getAttribute("user");
//		User user = new User();
//		try{
//			QueryRunner run = new QueryRunner(JdbcUtil.getDataSource());
//			Object o = run.query(sql,new ScalarHandler(),user.getClass(),uri);
//			int size = Integer.parseInt(o.toString());
//			if(size==0){
//				System.err.println("你没有权限....");
//			}else{
//				chain.doFilter(req, response);
//			}
//			
//		}catch(Exception e){
//			
//		}
//	}
	//***************************************************************************************************************
	//***************************************************************************************************************
	public void AutoFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 在这儿读取cookie
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取用户请求的uri
		String uri = req.getRequestURI();// 就是/LoginServlet
		if (req.getSession().getAttribute("exit") == null) {
			if (req.getSession().getAttribute("name") == null) {
				if (!uri.contains("/LoginServlet")) {
					// 获取所的有cookie
					Cookie[] cs = req.getCookies();
					if (cs != null) {
						for (Cookie c : cs) {
							if (c.getName().equals("autoLogin")) {// 如果存在自动登录的cookie
								String value = c.getValue();// 用户名称
								value = URLDecoder.decode(value, "UTF-8");
								// 登录成功是指
								req.getSession().setAttribute("name", value);
								break;
							}
						}
					}
				}
			}
		}else{
			req.getSession().removeAttribute("exit");
		}
		// 不管是否自动登录成
		chain.doFilter(request, response);
	}
	//***************************************************************************************************************
	//***************************************************************************************************************

//	public void AutoLoginFilter(ServletRequest request, ServletResponse response,
//			FilterChain chain) throws IOException, ServletException {
//		// 1.强转
//		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse resp = (HttpServletResponse) response;
//		// 2.操作
//		User u=new User();
//		req.getSession().setAttribute("user", u);
//		
//		// 2.1判断当前用户是否登录
//		User user = (User) req.getSession().getAttribute("user");
//		if (user == null) { // user为null说明用户没有登录，可以进行自动登录操作
//			// 2.2 得到访问的资源路径
//			String uri = req.getRequestURI();
//			String contextPath = req.getContextPath();
//			String path = uri.substring(contextPath.length());
//
//			if (!("/regist.jsp".equalsIgnoreCase(path)
//					|| "/login".equalsIgnoreCase(path) || "/regist"
//						.equalsIgnoreCase(path))) {
//				// 符合条件的是可以进行自动登录操作的.
//
//				// 2.3 得到cookie，从cookie中获取username,password
//				Cookie cookie = CookieUtils.findCookieByName(req.getCookies(), "autologin");
//
//				if (cookie != null) {
//					// 说明有用户名与密码，可以进行自动登录
//					String username = URLDecoder.decode(cookie.getValue()
//							.split("%itcast%")[0], "utf-8");
//					String password = cookie.getValue().split("%itcast%")[1];
//				}
//
//			}
//
//		}
//		// 3.放行
//		chain.doFilter(req, resp);
//	}
	//***************************************************************************************************************
	//***************************************************************************************************************
	//url-pattern=/*
	public void IpFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("ip")==null){
			req.getSession().setAttribute("ip",req.getRemoteAddr());
		}
		chain.doFilter(req, response);
	}
	//***************************************************************************************************************
	//***************************************************************************************************************
	public void OneFilter(ServletRequest request, ServletResponse response,FilterChain chain)
			throws IOException, ServletException {
System.err.println("1：正在执行过虑："+this+","+request);
String name = conf.getInitParameter("name");
System.err.println("doFilter :  name is:"+name);

//执行下一个过虑器，如果没有下一个过虑器则执行目标的servlet
chain.doFilter(request, response);

System.err.println("3：目标组件，执行完成了...");

}
	//***************************************************************************************************************
	//***************************************************************************************************************
	public void UserLoginFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//验证用户是否已经登录
		HttpServletRequest req = (HttpServletRequest) request;
		if(req.getSession().getAttribute("user")==null){
			String loginPath = conf.getInitParameter("loginPage");
			if(req.getSession().getAttribute("currentBook")==null){
				//解析url
				String uri = req.getRequestURI();
				//解析参数
				String param = req.getQueryString();
				String bookId = req.getParameter("id");
				req.getSession().setAttribute("currentBook",bookId);
			}
			req.getRequestDispatcher(loginPath).forward(request, response);
		}else{
			chain.doFilter(request, response);
		}
	}
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************
	//***************************************************************************************************************

}
