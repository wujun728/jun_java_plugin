/*   
 * Project: OSMP
 * FileName: CacheServlet.java
 * version: V1.0
 */
package com.osmp.cache.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.osmp.cache.core.CacheManager;
import com.osmp.cache.core.CacheableDefine;
import com.osmp.cache.utils.IOUtils;

/**
 * Description:
 * 
 * @author: wangkaiping
 * @date: 2014年8月8日 下午2:56:06
 */

public class CacheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final static String RESOURCE_PATH = "page";

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String contextPath = req.getContextPath();
		String servletPath = req.getServletPath();
		String requestURI = req.getRequestURI();
		resp.setCharacterEncoding("utf-8");
		if (contextPath == null) { // root context
			contextPath = "";
		}
		String uri = contextPath + servletPath;
		String path = requestURI.substring(contextPath.length() + servletPath.length());
		if ("".equals(path)) {
			if (contextPath == null || contextPath.equals("") || contextPath.equals("/")) {
				resp.sendRedirect("/osmp/index.html");
			} else {
				resp.sendRedirect("osmp/index.html");
			}
			return;
		}

		if ("/".equals(path)) {
			resp.sendRedirect("index.html");
			return;
		}

		if (path.indexOf(".json") >= 0) {
			String fullUrl = path;
			if (req.getQueryString() != null && req.getQueryString().length() > 0) {
				fullUrl += "?" + req.getQueryString();
			}
			String result = "";
			if ("/list.json".equals(fullUrl)) {
				result = this.getList();
			} else if ("/update.json".equals(fullUrl)) {
				result = this.update(req);
			}
			resp.getWriter().print(result);
			return;
		}

		returnResourceFile(path, uri, req, resp);
	}

	/**
	 * 加载界面文件，本想通过freemarker，为了最小依赖原则，通过原始的方式进行加载
	 * 
	 * @Description:
	 * @param @param fileName
	 * @param @param uri
	 * @param @param req
	 * @param @param response
	 * @param @throws ServletException
	 * @param @throws IOException
	 * @return void
	 * @throws
	 */
	private void returnResourceFile(String fileName, String uri, HttpServletRequest req, HttpServletResponse response)
			throws ServletException, IOException {
		if (fileName.endsWith(".jpg") || fileName.endsWith(".gif") || fileName.endsWith(".png")) {
			byte[] bytes = IOUtils.readByteArrayFromResource(RESOURCE_PATH + fileName);
			if (bytes != null) {
				response.getOutputStream().write(bytes);
			}
			return;
		}

		String text = IOUtils.readFromResource(RESOURCE_PATH + fileName);
		if ("/edit.html".equals(fileName)) {
			text = this.parseEdit(text, req);
		}
		if (fileName.endsWith(".css")) {
			response.setContentType("text/css;charset=utf-8");
		} else if (fileName.endsWith(".js")) {
			response.setContentType("text/javascript;charset=utf-8");
		}
		response.getWriter().write(text);
	}

	private ApplicationContext getApplicationContext() {
		return WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
	}

	/**
	 * 这里为了保持最小依赖原则，手动进行赋值生成界面！
	 * 
	 * @Description:
	 * @param @param text
	 * @param @param req
	 * @return void
	 * @throws
	 */
	private String parseEdit(String text, HttpServletRequest req) {
		String queryStr = req.getQueryString();
		if (null != queryStr && !"".equals(queryStr.trim())) {
			String[] arr = queryStr.split("&");
			if (null != arr && arr.length > 0) {
				String id = "";
				for (String temp : arr) {
					if (temp.indexOf("id=") != -1) {
						id = temp.replaceAll("id=", "");
						break;
					}
				}
				CacheManager cacheManager = this.getApplicationContext().getBean(CacheManager.class);
				CacheableDefine cacheDefine = cacheManager.getCacheableDefineByID(id);
				if (cacheDefine != null) {
					text = text.replace("${id}", cacheDefine.getId())
							.replace("${timeToLive}", String.valueOf(cacheDefine.getTimeToLive()))
							.replace("${timeToIdle}", String.valueOf(cacheDefine.getTimeToIdle()));
					if (CacheableDefine.STATE_OPEN == cacheDefine.getState()) {
						text = text.replace("<option value=\"0\" selected=\"selected\">关闭</option>",
								"<option value=\"0\">关闭</option>");
					} else {
						text = text.replace("<option value=\"1\" selected=\"selected\">关闭</option>",
								"<option value=\"1\">关闭</option>");
					}
				}
			}
		}
		return text;
	}

	/**
	 * 得到所有缓存注解列表
	 * 
	 * @Description:
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String getList() {
		CacheManager cacheManager = null;
		try {
			cacheManager = this.getApplicationContext().getBean(CacheManager.class);
		} catch (BeansException e) {
			e.printStackTrace();
			return "";
		}
		List<CacheableDefine> list = cacheManager.getCacheableDefines();
		List<Map<String, Object>> rows = new ArrayList<Map<String, Object>>();
		for (CacheableDefine d : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", d.getId());
			map.put("name", d.getName());
			map.put("method", d.getMethod().getDeclaringClass().getName() + "." + d.getMethod().getName());
			map.put("timeToLive", d.getTimeToLive());
			map.put("timeToIdle", d.getTimeToIdle());
			map.put("state", d.getState());
			rows.add(map);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", rows);
		JSONObject json = JSONObject.fromObject(map);
		String result = json.toString();
		return result;
	}

	/**
	 * 更新缓存
	 * 
	 * @Description:
	 * @param @param req
	 * @param @return
	 * @return String
	 * @throws
	 */
	private String update(HttpServletRequest req) {
		String id = req.getParameter("id");
		String timeToLive = req.getParameter("timeToLive");
		String timeToIdle = req.getParameter("timeToIdle");
		String state = req.getParameter("state");
		String result = "";
		if (null == id || null == timeToLive || null == timeToIdle || null == state) {
			result = "提交数据不合法！";
		} else {
			CacheManager cacheManager = this.getApplicationContext().getBean(CacheManager.class);
			CacheableDefine cacheDefine = cacheManager.getCacheableDefineByID(id);
			cacheDefine.setState(Integer.valueOf(state));
			cacheDefine.setTimeToIdle(Integer.valueOf(timeToIdle));
			cacheDefine.setTimeToLive(Integer.valueOf(timeToLive));
			result = "更新缓存成功!";
		}

		return result;
	}
}
