package com.sam.demo.spring.boot.mvc.web.message.converter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.google.gson.Gson;
import com.sam.demo.spring.boot.mvc.web.controller.User;

public class CustomMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	private Gson gson;

	public void setGson(Gson gson) {
		this.gson = gson;
	}
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return User.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		String contentStr = IOUtils.toString(request.getInputStream(), "utf-8");
		
		User user = gson.fromJson(contentStr, User.class);
		
		return user;
	}

}
