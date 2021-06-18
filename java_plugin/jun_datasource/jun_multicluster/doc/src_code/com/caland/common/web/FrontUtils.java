package com.caland.common.web;


import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * 前台工具类
 */
public class FrontUtils {
	/**
	 * 
	 */
	public static final String MESSAGE = "message";
	public static final String LOGIN_URL = "user/login.html";
	/**
	 * 返回页面
	 */
	public static final String RETURN_URL = "returnUrl";

	/**
	 * 显示登录页面
	 * 
	 * @param request
	 * @param model
	 * @param site
	 * @param message
	 * @return
	 */
	public static String showLogin(HttpServletRequest request) {
		StringBuilder buff = new StringBuilder("redirect:");
		buff.append(LOGIN_URL).append("?");
		buff.append(RETURN_URL).append("=");
		buff.append(RequestUtils.getLocation(request));
		System.out.println(buff.toString());
		return buff.toString();
	}
}
