package com.jun.plugin.base;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
// 1202402625588
@SuppressWarnings("serial")
@WebServlet(name = "BaseServlet", value = { "/baseServlet" })
// http://localhost:10080/jun_base/bizServlet?method=XmlFileServlet
public class BaseServlet extends HttpServlet {
	

	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String methodName = request.getParameter("method");
		if (methodName == null || methodName.trim().equals("")) {
			methodName = "doGet";// 默认方法
		}
		try {
			System.err.println("Method="+methodName+", Start::Name=" + Thread.currentThread().getName() + "::ID=" + Thread.currentThread().getId());
			long startTime = System.currentTimeMillis();
			Method mm = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
			MyRequest mr = new MyRequest(request);
			mm.invoke(this, mr, response);
			long endTime = System.currentTimeMillis();
			System.err.println("URL:"+request.getRequestURI()+",Method="+methodName+", Start::Name=" + Thread.currentThread().getName() + "::ID=" + Thread.currentThread().getId() + "::Time Taken=" + (endTime - startTime) + " ms.");
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
	
	StringBuilder json=new StringBuilder();
	protected void jsonInit() {
		json.delete(0, json.length());
	}
	public void outJson(HttpServletResponse response,Object obj) {
		try {
			String json =  JSON.toJSONStringWithDateFormat(obj, "yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter pw= response.getWriter();
			System.err.println("json="+json.toString());
			pw.write(json.toString());
			pw.close();
			/*ServletOutputStream out = response.getOutputStream();
			long data = new Date().getTime(); // "Hello World !!!";
			out.print(data);*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	static StringBuilder xml=new StringBuilder();
	public void xmlInit() {
		xml.delete(0, xml.length());
	}
	public void outXml(HttpServletResponse response,Object xml) {
		try {
			response.setContentType("text/xml; charset=UTF-8");
			PrintWriter pw= response.getWriter();
			pw.write(xml.toString());
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void threadSleepSecs(int secs) {
		try {
			Thread.sleep(secs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	//@Override
		/*public void serviceV2(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
		}*/
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