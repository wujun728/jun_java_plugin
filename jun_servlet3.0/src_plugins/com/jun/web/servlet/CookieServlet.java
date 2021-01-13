package com.jun.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jun.plugin.base.BaseServlet;


//http://localhost:18080/jun_plugin_base/cookie?method=addCookie&username=abc
@SuppressWarnings("serial")
@WebServlet(name = "CookieServlet", value = { "/cookie" })
public class CookieServlet extends BaseServlet {

	public void addCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		Cookie cookie = new Cookie("test", "wj");
		cookie.setMaxAge(60 * 30);
		cookie.setMaxAge(-1);
		// cookie.setDomain(".baidu.com");
		response.addCookie(cookie);
	}

	public void CnCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// 读取cookie
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				System.out.println(c.getName() + ":" + c.getValue());
				// 获得cn的值，然后解码
				if ("cn".equals(c.getName())) {
					String value = URLDecoder.decode(c.getValue(), "UTF-8");
					System.out.println(value);
				}
			}
		}
		String data = "中文";
		String returnData = URLEncoder.encode(data, "UTF-8"); // base64
		Cookie cookie = new Cookie("cn", returnData);
		response.addCookie(cookie);
	}

	public void testCookieMaxLength(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// 拼凑数据，获得4kb字符串
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < 1024 * 5; i++) {
			buf.append("a");
		}
		String data = buf.toString();
		Cookie cookie = new Cookie("max", data);
		cookie.setMaxAge(60 * 60);
		response.addCookie(cookie);
	}

	public void getAllCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cs = request.getCookies();
		if (cs != null) {
			for (Cookie c : cs) {
				System.err.println(c.getName() + "," + c.getValue());
				System.err.println("age:" + c.getMaxAge());// 为了保证cookie的安全，这个值永远是-1
				System.err.println("path:" + c.getPath());// 永远都返回null
				System.err.println("---------------------------");
			}
		}
	}

	public void delCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cookieName = request.getParameter("cookieName");
		Cookie cookie = new Cookie(cookieName, "");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		// response.sendRedirect(request.getContextPath() + "/products.jsp");
	}

	public void Access(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		Cookie tagetCookie = findTargetCookie(cookies, "lastvisit");
		response.setContentType("text/html;charset=utf-8");
		if (tagetCookie == null) {
			response.getWriter().print(" 0000000000000  ....");
		} else {
			String value = tagetCookie.getValue();
			long time = Long.parseLong(value);
			response.getWriter().print("111111111111111  : " + new Date(time).toLocaleString());
		}
		Cookie cookie = new Cookie("lastvisit", System.currentTimeMillis() + "");
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 7);
		response.addCookie(cookie);
	}

	public static Cookie findTargetCookie(Cookie[] cookies, String name) {
		if (cookies == null) {
			return null;
		}
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(name)) {
				return cookie;
			}
		}
		return null;
	}

	public void SaxAgeCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				out.print(c.getName() + ":" + c.getValue() + "<br/>");
			}
		} else {
			out.print("不存在cookie");
		}
		Cookie cookie = new Cookie("gf", "fengjie");
		cookie.setMaxAge(60 * 60 * 24);
		response.addCookie(cookie);
	}

	public void ShowInfoSerlvet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		Cookie[] cookies = request.getCookies();
		Cookie targetCookie = CookieServlet.findTargetCookie(cookies, "history");
		if (targetCookie == null) {
			Cookie cookie = new Cookie("history", id);
			cookie.setMaxAge(60 * 60 * 24); // ���� ��Ч��
			cookie.setPath("/"); // ������Ч·��
			response.addCookie(cookie);
		} else {
			String value = targetCookie.getValue(); // 1-5-3-2
			String[] records = value.split("-");
			if (!checkExistId(records, id)) {
				Cookie cookie = new Cookie("history", value + "-" + id);
				cookie.setMaxAge(60 * 60 * 24); // ���� ��Ч��
				cookie.setPath("/"); // ������Ч·��
				response.addCookie(cookie);
			}
		}
		response.setContentType("text/html;charset=utf-8");
		response.getWriter().print("����, �����Ʒ�ɹ�.... <a href='/day10_cookie_session/products.jsp'>�������</a>");

	}

	private boolean checkExistId(String[] records, String id) {
		for (String record : records) {
			if (record.equals(id)) {
				return true;
			}
		}
		return false;
	}

	public void getCookie(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				out.print("222222 --> " + c.getName() + " : " + c.getValue());
			}
		} else {
			out.print("没有222222 cookie");
		}

	}

	public void HistServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String curTime = format.format(new Date());
		Cookie[] cookies = request.getCookies();
		String lastTime = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("lastTime")) {
					lastTime = cookie.getValue();// �ϴη��ʵ�ʱ��
					response.getWriter().write("��ӭ���������ϴη��ʵ�ʱ��Ϊ��" + lastTime + ",��ǰʱ��Ϊ��" + curTime);
					cookie.setValue(curTime);
					cookie.setMaxAge(1 * 30 * 24 * 60 * 60);
					response.addCookie(cookie);
					break;
				}
			}
		}
		if (cookies == null || lastTime == null) {
			response.getWriter().write("�����״η��ʱ���վ����ǰʱ��Ϊ��" + curTime);
			Cookie cookie = new Cookie("lastTime", curTime);
			cookie.setMaxAge(1 * 30 * 24 * 60 * 60);// ����һ����
			response.addCookie(cookie);
		}
	}

	public String username = "";

	@SuppressWarnings("static-access")
	public boolean check(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		String cookieName = "username";
		// PrintWriter out = res.getWriter();
		try {
			Cookie[] myCookies = req.getCookies();
			this.username = this.getCookieValue(myCookies, cookieName, "not found");
		} catch (Exception e) {
			return false;
		}
		if (!this.username.equals(new String("not found"))) {
			return true;
		} else {
			return false;
		}

	}

	public static String getCookieValue(Cookie[] cookies, String cookieName, String defaultValue) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookieName.equals(cookie.getName()))
				return (cookie.getValue());
		}
		return (defaultValue);
	}


	public static String getCookieValue(HttpServletRequest request,String cookieName,String defaultValue)
	{		
		Cookie cookieList[]=request.getCookies();
		if(cookieList==null||cookieName==null)
			return "";
		for(int i= 0;i<cookieList.length;i++)
		{
			try{
			if(cookieList[i].getName().equals(cookieName))
				return java.net.URLDecoder.decode(cookieList[i].getValue(),"GBK");
			}
			catch(Exception e){
				e.printStackTrace();
			}
				
		}
		return "";
	}
	public static void setCookie(HttpServletResponse response,String cookieName,String cookieValue)
	{
		Cookie theCookie=new Cookie(java.net.URLEncoder.encode(cookieName),java.net.URLEncoder.encode(cookieValue));
		
		response.addCookie(theCookie);
	}
	public static void setCookie(HttpServletResponse response,String cookieName,String cookieValue,int cookieMaxage)
	{
		Cookie theCookie=new Cookie(java.net.URLEncoder.encode(cookieName),java.net.URLEncoder.encode(cookieValue));
		theCookie.setMaxAge(cookieMaxage);
		response.addCookie(theCookie);
	}
}
