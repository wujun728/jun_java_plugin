package com.erp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.jun.plugin.utils.ExceptionUtil;

/**
 * spring mvc异常捕获类
 * 
 * @author Wujun
 * 
 */
@Component
public class SpringMvcHandlerExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = Logger.getLogger(SpringMvcHandlerExceptionResolver.class);

	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		String exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
		logger.error(exceptionMessage);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("exceptionMessage", exceptionMessage);
		return new ModelAndView("/error/exceptionMessage", model);
	}

}
