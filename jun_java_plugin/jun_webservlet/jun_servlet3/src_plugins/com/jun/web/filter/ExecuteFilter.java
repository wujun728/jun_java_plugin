package com.jun.web.filter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

public class ExecuteFilter implements Filter {
	private FilterConfig filterConfig;
	private String encoding;
	private Map<String, byte[]> cache = new HashMap<String, byte[]>();
	public void init(FilterConfig config) throws ServletException {
		encoding = config.getInitParameter("bm");
		this.filterConfig = config;
		System.err.println("过虑器初始化:" + this);
		// 获取name的值：
		String name = config.getInitParameter("name");
		System.err.println("filter name is:" + name);
		System.err.println("----------------------------");
		Enumeration<String> en = config.getInitParameterNames();
		while (en.hasMoreElements()) {
			String paramName = en.nextElement();
			String val = config.getInitParameter(paramName);
			System.err.println(paramName + "=" + val);
		}
	}
	public void destroy() {
		System.err.println("过虑器销毁....." + this);
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.err.println("Filter开始");
//		chain.doFilter(request, response);
//		this.doAuthFilter(request, response, chain);
//		this.doAutoFilter(request, response, chain);
//		this.doAutoLoginFilter(request, response, chain);;
//		this.doCacheFilter(request, response, chain);
//		this.doUTF8Filter(request, response, chain);
		//this.doEncodingFilterForGet(request, response, chain);
//		this.doForwardFilter(request, response, chain);
//		this.doIPFilter(request, response, chain);
//		this.doRoleFilter(request, response, chain);
//		this.doURIFilter(request, response, chain);
		this.doEncodingFilter(request, response, chain);
		//this.doIpAddressFilter(request, response, chain);
		//this.doLoginFilter(request, response, chain);
		//this.doTxFilter(request, response, chain);
		System.err.println("Filter结束");
	}
 	
	/**
	 * 缓存
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doCacheFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Calendar cl = Calendar.getInstance();
		cl.add(Calendar.DATE, 2);
		long time = cl.getTimeInMillis();
		response.setDateHeader("expires", time);
		response.setHeader("expires",""+time);
//		resp.setHeader("expires","-1");
		response.setHeader("pragma","no-cache");
		response.setHeader("cache-control","no-cache");
		String uri = request.getRequestURI();
		byte[] data = cache.get(uri);
		if (data == null) {
			MyResponse myResponse = new MyResponse(response);
			chain.doFilter(request, myResponse);
			data = myResponse.getBuffer();
			cache.put(uri, data);
			System.out.println("");
		}
//		response.getOutputStream().write(data);
		chain.doFilter(request, response);
	}
	
	

	/**
	 * 编码
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doUTF8Filter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		chain.doFilter(request, response);
	}

	/**
	 * 编码
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doEncodingFilterForGet(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);// 对get没有效
		response.setContentType("text/html;charset=" + encoding);
		// response.setContentType("text/html;charset=UTF-8");
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getMethod().equals("GET")) {
//			request = new MyRequest(req);
		}
		chain.doFilter(request, response);
	}
	

	/**
	 * 编码
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doEncodingFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		MyRequestExe myRequest = new MyRequestExe(request);
		chain.doFilter(myRequest.getProxy(), response);
	}

	/**
	 * uri编码
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doURIFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String uri = request.getRequestURI();
		if (uri != null && uri.endsWith("jsp")) {
			response.setHeader("expires", "-1");
			response.setHeader("cache-control", "no-cache");
			response.setHeader("pragma", "no-cache");
		} else if (uri != null && uri.endsWith("html")) {
			String strHtml = filterConfig.getInitParameter("html");
			long time = System.currentTimeMillis() + Integer.parseInt(strHtml) * 1000;
			response.setDateHeader("expires", time);
			response.setHeader("cache-control", time / 1000 + "");
			response.setHeader("pragma", time / 1000 + "");
		}
		chain.doFilter(request, response);
	}

	/**
	 * 跳转
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doForwardFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		if (username != null && password != null) {
			if (username.equals("jack") && password.equals("123")) {
				chain.doFilter(request, response);
			} else {
				request.setAttribute("message", "info");
				request.getRequestDispatcher("/LoginForm.jsp").forward(request, response);
			}
		} else {
			request.setAttribute("message", "null");
		}
	}

	/**
	 * 角色
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doRoleFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		request.setCharacterEncoding("UTF-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		if (username != null && password != null && role != null && username.trim().length() > 0 && password.trim().length() > 0 && role.trim().length() > 0) {
			if ("admin".equals(role)) {
				request.setAttribute("message", "<font color='blue'>" + username + "</font>");
				request.setAttribute("flag", "user");
			} else if ("guest".equals(role)) {
				request.setAttribute("message", "<font color='red'>" + username + "</font>");
				request.setAttribute("flag", "admin");
			}
			chain.doFilter(request, response);
		}
	}

	/**
	 * cookie过滤
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doAutoLoginFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		Cookie[] cookies = request.getCookies();
		Cookie userCookie = null;
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals("usernameAndPassword")) {
					userCookie = c;
					break;
				}
			}
			if (userCookie != null) {
				String usernameAndPassword = userCookie.getValue();
				String[] both = usernameAndPassword.split("_");
				String username = both[0];
				String password = both[1];
				if (username.equals("jack") && password.equals("123")) {
					request.getSession().setAttribute("username", username);
				}
			}
		}
		chain.doFilter(request, response);
	}


	/**
	 * IP
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
//	public void doIpAddressFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		try {
//			HttpServletRequest request = (HttpServletRequest) req;
//			HttpServletResponse response = (HttpServletResponse) res;
//			HttpSession session = request.getSession();
//			Address address = (Address) session.getAttribute("address");
//			if (address == null) {
//				String ip = request.getRemoteAddr();
//				BbsService bbsService = new BbsService();
//				address = bbsService.findAddressByIP(ip);
//				session.setAttribute("address", address);
//			}
//			chain.doFilter(request, response);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	// url-pattern=/*
	/**
	 * IP
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doIPFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (req.getSession().getAttribute("ip") == null) {
			req.getSession().setAttribute("ip", req.getRemoteAddr());
		}
		chain.doFilter(req, response);
	}
	
	

	/**
	 * 自动登陆
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doAutoFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// 在这儿读取cookie
		HttpServletRequest req = (HttpServletRequest) request;
		// 获取用户请求的uri
		String uri = req.getRequestURI();// 就是/LoginServlet
		if (req.getSession().getAttribute("exit") == null) {
			if (req.getSession().getAttribute("name") == null) {
				if (!uri.contains("/LoginServlet")) {
					// 获取�?��有cookie
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
		// 不管是否自动登录�?
		chain.doFilter(request, response);
	}
	

	/**
	 * 权限过滤
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
//	public void doAuthFilter(ServletRequest request, ServletResponse response,
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
//		User user = (User) req.getSession().getAttribute("user");
//		try{
//			QueryRunner run = new QueryRunner(DataSourceUtils.getDatasSource());
//			Object o = run.query(sql,new ScalarHandler(),user.getId(),uri);
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
	
	/**
	 * 登陆过滤
	 * @param req
	 * @param res
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doLoginFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//将request强转成htt...
		HttpServletRequest req = (HttpServletRequest) request;
		//获取session
		HttpSession ss = req.getSession();
		//从session中获取user
		if(ss.getAttribute("user")==null){
			System.err.println("你还没有登录");
			req.getSession().setAttribute("msg", "请你先登录");
			//重定向到登录
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.sendRedirect(req.getContextPath()+"/LoginForm.jsp");
		}else{
			//放行
			chain.doFilter(request, response);
		}
	}
}




// 声明包装类
class MyRequestExe extends HttpServletRequestWrapper {
	private String[] ss;
	private HttpServletRequest request;

	public MyRequestExe(HttpServletRequest request) {
		// 读取数据库，将读取的数据放到缓存
		super(request);
		ss = new String[]{"SB", "江泽民", "小胡"};
		this.request = request;
	}

	// 增强getParamter
	public String getParameter2(String name) {
		String val = super.getParameter(name);
		try {
			val = new String(val.getBytes("ISO-8859-1"), super.getCharacterEncoding());
			for (String s : ss) {
				val = val.replace(s, "****");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return val;
	}

	// ��д����ķ���
	public String getParameter(String name) {// �?�������
		String value = null;
		// [GET/POST]
		String method = request.getMethod();
		if ("GET".equals(method)) {
			try {
				value = request.getParameter(name);// ����
				byte[] buf = value.getBytes("ISO8859-1");
				value = new String(buf, "UTF-8");// ����
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if ("POST".equals(method)) {
			try {
				request.setCharacterEncoding("UTF-8");
				value = request.getParameter(name);// ����
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		value = filter(value);
		return value;
	}
	// ת�巽��
	public String filter(String message) {
		if (message == null)
			return (null);
		char content[] = new char[message.length()];
		message.getChars(0, message.length(), content, 0);
		StringBuffer result = new StringBuffer(content.length + 50);
		for (int i = 0; i < content.length; i++) {
			switch (content[i]) {
				case '<' :
					result.append("&lt;");
					break;
				case '>' :
					result.append("&gt;");
					break;
				case '&' :
					result.append("&amp;");
					break;
				case '"' :
					result.append("&quot;");
					break;
				default :
					result.append(content[i]);
			}
		}
		return (result.toString());
	}

	public HttpServletRequest getProxy() {
		return (HttpServletRequest) Proxy.newProxyInstance(MyRequest.class.getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler() {
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				if ("getParameter".equals(method.getName())) {
					// [POST��GET]
					String m = request.getMethod();
					if ("get".equalsIgnoreCase(m)) {
						String value = request.getParameter((String) args[0]);
						byte[] buf = value.getBytes("ISO8859-1");
						value = new String(buf, "UTF-8");
						return value;
					} else if ("post".equalsIgnoreCase(m)) {
						request.setCharacterEncoding("UTF-8");
						String value = request.getParameter((String) args[0]);
						return value;
					}
				} else {
					return method.invoke(request, args);
				}
				return null;
			}
		});
	}
}


class MyResponseInner extends HttpServletResponseWrapper {
	private HttpServletResponse response;
	private ByteArrayOutputStream bout = new ByteArrayOutputStream();
	private PrintWriter pw;
	public MyResponseInner(HttpServletResponse response) {
		super(response);
		this.response = response;
	}
	public ServletOutputStream getOutputStream() throws IOException {
		return new MyServletOutputStream(bout);
	}
	public PrintWriter getWriter() throws IOException {
		pw = new PrintWriter(new OutputStreamWriter(bout, "UTF-8"));
		return pw;
	}
	public byte[] getBuffer() {
		if (pw != null) {
			pw.flush();
		}
		return bout.toByteArray();
	}
	
}

class MyServletOutputStreamInner extends ServletOutputStream {
	private ByteArrayOutputStream bout;
	public MyServletOutputStreamInner(ByteArrayOutputStream bout) {
		this.bout = bout;
	}
	public void write(int b) throws IOException {
	}
	public void write(byte[] bytes) throws IOException {
		bout.write(bytes);
		bout.flush();
	}
}


//���л��湦�ܵ�ServletOutputStream
class MyServletOutputStream extends ServletOutputStream {
	private ByteArrayOutputStream bout;
	public MyServletOutputStream(ByteArrayOutputStream bout) {
		this.bout = bout;
	}
	@Override
	public void write(int b) throws IOException {
	}
	// ���ֽ����д�뻺��
	public void write(byte[] buf) throws IOException {
		bout.write(buf);
		bout.flush();
	}
}
/*
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
}*/