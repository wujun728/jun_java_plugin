package com.jun.plugin.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import lombok.extern.slf4j.Slf4j;

/**
 * 身份验证
 * @ClassName: AccessTokenVerifyInterceptor
 * @Description:
 * @author: renkai721
 * @date: 2018年6月26日 上午9:27:28
 */
@Component
@Slf4j
public class AccessTokenVerifyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String url = request.getRequestURI();
		log.info("客户端请求的URL=" + url);
		return true;
	}
}
