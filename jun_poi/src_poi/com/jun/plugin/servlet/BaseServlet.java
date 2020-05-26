package com.jun.plugin.servlet;

import java.io.IOException;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
// 1202402625588
@SuppressWarnings("serial")
@WebServlet(name = "BaseServlet", value = { "/baseServlet" })
// http://localhost:10080/jun_base/bizServlet?method=XmlFileServlet
public class BaseServlet extends HttpServlet {

	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String methodName = req.getParameter("method");
		if (methodName == null || methodName.trim().equals("")) {
			methodName = "doGet";// 默认方法
		}
		try {
			Method mm = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			MyRequest mr = new MyRequest(req);
			mm.invoke(this, mr, resp);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException("没有此方法：" + e.getMessage(), e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException("目标方法执行失败：" + e.getMessage(), e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException("你可能访问了一个私有的方法：" + e.getMessage(), e);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
	
	//@Override
	public void serviceV2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTf-8");
		String methodName = req.getParameter("cmd");
		if (methodName == null || methodName.trim().equals("")) {
			methodName = "execute";
		}
		try {
			Method m = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			m.invoke(this, req, resp);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

class MyRequest extends HttpServletRequestWrapper {
	private HttpServletRequest req;

	public MyRequest(HttpServletRequest request) {
		super(request);
		this.req = request;
	}

	@Override
	public String getParameter(String name) {
		String value = req.getParameter(name);
		if (req.getMethod().equals("GET")) {
			System.err.println("转码");
			try {
				value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
			} catch (Exception e) {
			}
		}
		return value;
	}

	@Override
	public String[] getParameterValues(String name) {
		String[] vals = req.getParameterValues(name);
		if (req.getMethod().equals("GET")) {
			for (int i = 0; i < vals.length; i++) {
				try {
					vals[i] = new String(vals[i].getBytes("ISO-8859-1"), req.getCharacterEncoding());
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
		return vals;
	}

	@Override
	public Map getParameterMap() {
		Map<String, String[]> mm = req.getParameterMap();
		if (req.getMethod().equals("GET")) {
			Iterator<String[]> it = mm.values().iterator();
			while (it.hasNext()) {
				String[] ss = it.next();
				for (int i = 0; i < ss.length; i++) {
					try {
						ss[i] = new String(ss[i].getBytes("ISO-8859-1"), req.getCharacterEncoding());
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return mm;
	}
}

/*
 * @Override public void doGet(HttpServletRequest req, HttpServletResponse resp)
 * throws ServletException, IOException {
 * 
 * this.doPost(req, resp); }
 * 
 * @Override public void doPost(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException { try { String method =
 * req.getParameter("method"); // this.getClass() 获得当前实例的字节码 Class clazz =
 * this.getClass();
 * 
 * // 获得相应的方法 Method clazzMethod = clazz.getMethod(method,
 * HttpServletRequest.class, HttpServletResponse.class); // 执行
 * clazzMethod.invoke(this, req, resp); // 实际参数
 * 
 * } catch (Exception e) { e.printStackTrace(); }
 * 
 * }
 */

/*
 * @Override public void service(HttpServletRequest req, HttpServletResponse
 * resp) throws ServletException, IOException {
 * req.setCharacterEncoding("UTf-8"); String methodName =
 * req.getParameter("cmd"); if(methodName==null ||
 * methodName.trim().equals("")){ methodName="execute"; } try{ Method m =
 * this.getClass().getMethod( methodName,
 * HttpServletRequest.class,HttpServletResponse.class); m.invoke(this,req,resp);
 * }catch(Exception e){ throw new RuntimeException(e); } }
 */