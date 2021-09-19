package com.caland.common.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 后台（管理员）本地化信息拦截器
 */
public class AdminLocaleInterceptor extends HandlerInterceptorAdapter {
	/**
	 * 本地化字符串在ModelMap中的名称
	 */
	public static final String LOCALE = "locale";

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}
}