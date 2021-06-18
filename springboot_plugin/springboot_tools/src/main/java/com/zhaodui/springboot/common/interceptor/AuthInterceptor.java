package com.zhaodui.springboot.common.interceptor;

import com.alibaba.druid.util.StringUtils;
import com.zhaodui.springboot.common.controller.ExceptionController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Wujun
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		log.info("===============Shiro权限认证开始============");


		return true;
	}
}
