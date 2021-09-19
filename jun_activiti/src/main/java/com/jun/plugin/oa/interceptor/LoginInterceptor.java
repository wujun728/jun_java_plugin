package com.jun.plugin.oa.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jun.plugin.oa.entity.User;
import com.jun.plugin.oa.util.BeanUtils;
import com.jun.plugin.oa.util.UserUtil;

public class LoginInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		User user = UserUtil.getUserFromSession();
		if(!BeanUtils.isBlank(user)){
			return true;
		}
		response.sendRedirect(request.getContextPath() + "/userAction/login_view");
		return false;
	}

}
