package com.springboot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.alibaba.fastjson.JSONArray;

public abstract class BaseController {
	private static Logger log = Logger.getLogger(BaseController.class);
	private static ThreadLocal<ServletRequest> currentRequest = new ThreadLocal<ServletRequest>();
	private static ThreadLocal<ServletResponse> currentResponse = new ThreadLocal<ServletResponse>();

	/**
	 * init request,respone
	 * 
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void init(HttpServletRequest request, HttpServletResponse response) {
		currentRequest.set(request);
		currentResponse.set(response);
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest) currentRequest.get();
	}

	public HttpServletResponse getResponse() {
		return (HttpServletResponse) currentResponse.get();
	}

	/**
	 * Stores an attribute in this request
	 * 
	 * @param name
	 *            a String specifying the name of the attribute
	 * @param value
	 *            the Object to be stored
	 */
	public BaseController setAttr(String name, Object o) {
		getRequest().setAttribute(name, o);
		return this;
	}

	/**
	 * Returns the value of the named attribute as an Object, or null if no
	 * attribute of the given name exists.
	 * 
	 * @param name
	 *            a String specifying the name of the attribute
	 * @return an Object containing the value of the attribute, or null if the
	 *         attribute does not exist
	 */
	public <T> T getAttr(String name) {
		return (T) getRequest().getAttribute(name);
	}

	/**
	 * Removes an attribute from this request
	 * 
	 * @param name
	 *            a String specifying the name of the attribute to remove
	 */
	public BaseController removeAttr(String name) {
		getRequest().removeAttribute(name);
		return this;
	}

	public BaseController setSessionAttr(String name, Object value) {
		getRequest().getSession().setAttribute(name, value);
		return this;
	}

	public <T> T getSessionAttr(String name) {
		HttpSession session = getRequest().getSession(false);
		return session != null ? (T) session.getAttribute(name) : null;
	}

	/**
	 * Remove Object in session.
	 * 
	 * @param key
	 *            a String specifying the key of the Object stored in session
	 */
	public BaseController removeSessionAttr(String name) {
		HttpSession session = getRequest().getSession(false);
		if (session != null)
			session.removeAttribute(name);
		return this;
	}

	/**
	 * Returns the value of a request parameter as a String, or null if the
	 * parameter does not exist.
	 * <p>
	 * You should only use this method when you are sure the parameter has only
	 * one value. If the parameter might have more than one value, use
	 * getParaValues(java.lang.String).
	 * <p>
	 * If you use this method with a multivalued parameter, the value returned
	 * is equal to the first value in the array returned by getParameterValues.
	 * 
	 * @param name
	 *            a String specifying the name of the parameter
	 * @return a String representing the single value of the parameter
	 */
	public String getPara(String name) {
		return getRequest().getParameter(name);
	}

	public String getPara(String name, String defaultValue) {
		String result = getRequest().getParameter(name);
		return result != null && !"".equals(result) ? result : defaultValue;
	}

	/**
	 * Returns the value of a request parameter and convert to Long.
	 * 
	 * @param name
	 *            a String specifying the name of the parameter
	 * @return a Integer representing the single value of the parameter
	 */
	public Long getParaToLong(String arg0) {
		return toLong(getRequest().getParameter(arg0), null);
	}

	/**
	 * Returns the value of a request parameter and convert to Integer.
	 * 
	 * @param name
	 *            a String specifying the name of the parameter
	 * @return a Integer representing the single value of the parameter
	 */
	public Integer getParaToInt(String name) {
		return toInt(getRequest().getParameter(name), null);
	}

	/**
	 * Returns an array of String objects containing all of the values the given
	 * request parameter has, or null if the parameter does not exist. If the
	 * parameter has a single value, the array has a length of 1.
	 * 
	 * @param name
	 *            a String containing the name of the parameter whose value is
	 *            requested
	 * @return an array of String objects containing the parameter's values
	 */
	public String[] getParaValues(String name) {
		return getRequest().getParameterValues(name);
	}

	/**
	 * Returns an array of Integer objects containing all of the values the
	 * given request parameter has, or null if the parameter does not exist. If
	 * the parameter has a single value, the array has a length of 1.
	 * 
	 * @param name
	 *            a String containing the name of the parameter whose value is
	 *            requested
	 * @return an array of Integer objects containing the parameter's values
	 */
	public Integer[] getParaValuesToInt(String name) {
		String[] values = getRequest().getParameterValues(name);
		if (values == null)
			return null;
		Integer[] result = new Integer[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Integer.parseInt(values[i]);
		return result;
	}

	public Long[] getParaValuesToLong(String name) {
		String[] values = getRequest().getParameterValues(name);
		if (values == null)
			return null;
		Long[] result = new Long[values.length];
		for (int i = 0; i < result.length; i++)
			result[i] = Long.parseLong(values[i]);
		return result;
	}

	/**
	 * Returns the value of a request parameter and convert to Boolean.
	 * 
	 * @param name
	 *            a String specifying the name of the parameter
	 * @return true if the value of the parameter is "true" or "1", false if it
	 *         is "false" or "0", null if parameter is not exists
	 */
	public Boolean getParaToBoolean(String name) {
		return toBoolean(getRequest().getParameter(name), null);
	}

	/**
	 * Returns the value of a request parameter and convert to Date.
	 * 
	 * @param name
	 *            a String specifying the name of the parameter
	 * @return a Date representing the single value of the parameter
	 * @throws Exception
	 */
	public Date getParaToDate(String name) throws Exception {
		try {
			return toDate(getRequest().getParameter(name), null);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Get cookie value by cookie name.
	 */
	public String getCookie(String name, String defaultValue) {
		Cookie cookie = getCookieObject(name);
		return cookie != null ? cookie.getValue() : defaultValue;
	}

	/**
	 * Get cookie value by cookie name.
	 */
	public String getCookie(String name) {
		return getCookie(name, null);
	}

	/**
	 * Get cookie object by cookie name.
	 */
	public Cookie getCookieObject(String name) {
		Cookie[] cookies = getRequest().getCookies();
		if (cookies != null)
			for (Cookie cookie : cookies)
				if (cookie.getName().equals(name))
					return cookie;
		return null;
	}

	/**
	 * Get cookie value by cookie name and convert to Integer.
	 */
	public Integer getCookieToInt(String name) {
		String result = getCookie(name);
		return result != null ? Integer.parseInt(result) : null;
	}

	/**
	 * Get cookie value by cookie name and convert to Integer.
	 */
	public Integer getCookieToInt(String name, Integer defaultValue) {
		String result = getCookie(name);
		return result != null ? Integer.parseInt(result) : defaultValue;
	}

	/**
	 * Get cookie value by cookie name and convert to Long.
	 */
	public Long getCookieToLong(String name) {
		String result = getCookie(name);
		return result != null ? Long.parseLong(result) : null;
	}

	/**
	 * Get cookie value by cookie name and convert to Long.
	 */
	public Long getCookieToLong(String name, Long defaultValue) {
		String result = getCookie(name);
		return result != null ? Long.parseLong(result) : defaultValue;
	}

	/**
	 * Set Cookie to response.
	 */
	public BaseController setCookie(Cookie cookie) {
		getResponse().addCookie(cookie);
		return this;
	}

	/**
	 * Remove Cookie.
	 */
	public BaseController removeCookie(String name) {
		return doSetCookie(name, null, 0, null, null, null);
	}

	/**
	 * Remove Cookie.
	 */
	public BaseController removeCookie(String name, String path) {
		return doSetCookie(name, null, 0, path, null, null);
	}

	/**
	 * Remove Cookie.
	 */
	public BaseController removeCookie(String name, String path, String domain) {
		return doSetCookie(name, null, 0, path, domain, null);
	}

	private BaseController doSetCookie(String name, String value, int maxAgeInSeconds, String path, String domain,
			Boolean isHttpOnly) {
		Cookie cookie = new Cookie(name, value);
		cookie.setMaxAge(maxAgeInSeconds);
		// set the default path value to "/"
		if (path == null) {
			path = "/";
		}
		cookie.setPath(path);

		if (domain != null) {
			cookie.setDomain(domain);
		}
		if (isHttpOnly != null) {
			cookie.setHttpOnly(isHttpOnly);
		}
		getResponse().addCookie(cookie);
		return this;
	}

	public void redirect(String url) {
		try {
			getResponse().sendRedirect(url);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	public void render(String arg0) {
		try {
			getRequest().getRequestDispatcher(arg0).forward(getRequest(), getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * Return renderJavaScript.
	 * 
	 * 
	 */
	public void renderJavascript(Object javascriptText) {
		render(getResponse(), "text/html;charset=UTF-8", javascriptText);
	}

	/**
	 * Return renderText.
	 * 
	 * 
	 */
	public void renderHtml(Object htmlText) {
		render(getResponse(), "text/html;charset=UTF-8", htmlText);
	}

	/**
	 * Return renderText.
	 * 
	 * 
	 */
	public void renderText(Object value) {
		render(getResponse(), "text/plain;charset=UTF-8", value);
	}

	/**
	 * Return renderJson. to fastJson and return Object
	 * 
	 * 
	 */
	public void renderJson(Object value) {
		render(getResponse(), "application/json;charset=UTF-8", JSONArray.toJSONString(value));
	}

	/**
	 * Return renderJson.and return String
	 * 
	 * 
	 */
	public void renderJson(String jsonText) {
		render(getResponse(), "application/json;charset=UTF-8", jsonText);
	}

	/**
	 * Return renderJson. jsonArray fastJson
	 * 
	 * 
	 */
	public void renderJson(Map<String, Object> value) {
		render(getResponse(), "application/json;charset=UTF-8", JSONArray.toJSONString(value));
	}

	/**
	 * Return renderXml.
	 * 
	 * 
	 */
	public void renderXml(Object key) {
		render(getResponse(), "text/xml;charset=UTF-8", key);
	}

	/**
	 * Return render.
	 * 
	 * 
	 */
	public void render(HttpServletResponse response, String contentType, Object obj) {
		response.setContentType(contentType);
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		try {
			response.getWriter().write(obj.toString());
		} catch (IOException e) {
			log.error(e.getMessage(), e);
			try {
				response.getWriter().close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public String readRequstJson(HttpServletRequest request) {
		StringBuffer json = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				json.append(line);
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
		}
		return json.toString();
	}

	private Integer toInt(String value, Integer defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n"))
				return -Integer.parseInt(value.substring(1));
			return Integer.parseInt(value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	private Long toLong(String value, Long defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim();
			if (value.startsWith("N") || value.startsWith("n"))
				return -Long.parseLong(value.substring(1));
			return Long.parseLong(value);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	private Boolean toBoolean(String value, Boolean defaultValue) {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			value = value.trim().toLowerCase();
			if ("1".equals(value) || "true".equals(value))
				return Boolean.TRUE;
			else if ("0".equals(value) || "false".equals(value))
				return Boolean.FALSE;
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw e;
		}
		return false;

	}

	private Date toDate(String value, Date defaultValue) throws Exception {
		try {
			if (value == null || "".equals(value.trim()))
				return defaultValue;
			return new java.text.SimpleDateFormat("yyyy-MM-dd").parse(value.trim());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(), e);
			throw e;
		}
	}

	public void crossDomian() {
		getResponse().setHeader("Access-Control-Allow-Origin", "*");
		getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		getResponse().setHeader("Access-Control-Max-Age", "3600");
		getResponse().setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires,Content-Type, X-E4M-With");
	}

}
