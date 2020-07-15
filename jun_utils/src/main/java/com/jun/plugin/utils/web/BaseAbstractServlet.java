package com.jun.plugin.utils.web;

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

/**
 * @author Administrator
 */
//@SuppressWarnings("serial")
//@WebServlet(name = "BaseServlet", value = { "/baseServlet"})
public abstract class BaseAbstractServlet extends HttpServlet {
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	// http://server:port/project/baseServlet?method=someMethod
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.service(req, resp);
	}

	// 要求子类必须要拥有exexute方法
	public abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;

	// http://localhost:9080/jun_demo/bizServlet?method=getClassInfo
	//http://server:port/project/someServlet?cmd=someMethod
	@Override
	public void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String methodName = req.getParameter("method");
		if (methodName == null || methodName.trim().equals("")) {
			methodName = "execute";// 默认方法
		}
		try {
			Method mm = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			// 声明包装类
			// MyRequest mr = new MyRequest(req);
			mm.invoke(this, req, resp);
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
	
	
	
}


//包装request
class MyRequestAB extends HttpServletRequestWrapper {
	private HttpServletRequest req;

	public MyRequestAB(HttpServletRequest request) {
		super(request);
		this.req = request;
	}

	// 修改getparameter方法
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
// 包装request
// class MyRequest extends HttpServletRequestWrapper {
// private HttpServletRequest req;
//
// public MyRequest(HttpServletRequest request) {
// super(request);
// this.req = request;
// }
//
// // 修改getparameter方法
// @Override
// public String getParameter(String name) {
// String value = req.getParameter(name);
// if (req.getMethod().equals("GET")) {
// System.err.println("转码");
// try {
// value = new String(value.getBytes("ISO-8859-1"), "UTF-8");
// } catch (Exception e) {
// }
// }
// return value;
// }
//
// @Override
// public String[] getParameterValues(String name) {
// String[] vals = req.getParameterValues(name);
// if (req.getMethod().equals("GET")) {
// for (int i = 0; i < vals.length; i++) {
// try {
// vals[i] = new String(vals[i].getBytes("ISO-8859-1"), req.getCharacterEncoding());
// } catch (UnsupportedEncodingException e) {
// e.printStackTrace();
// }
// }
// }
// return vals;
// }
//
// @Override
// public Map getParameterMap() {
// Map<String, String[]> mm = req.getParameterMap();
// if (req.getMethod().equals("GET")) {
// Iterator<String[]> it = mm.values().iterator();
// while (it.hasNext()) {
// String[] ss = it.next();
// for (int i = 0; i < ss.length; i++) {
// try {
// ss[i] = new String(ss[i].getBytes("ISO-8859-1"), req.getCharacterEncoding());
// } catch (UnsupportedEncodingException e) {
// e.printStackTrace();
// }
// }
// }
// }
// return mm;
// }
// }
