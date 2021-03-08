package com.chentongwei.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.chentongwei.common.enums.msg.ResponseEnum;
import com.chentongwei.common.entity.Result;
import com.chentongwei.common.exception.BussinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 统一异常处理器
 */
@Order(-1000)
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
	/**
	 * 异常日志
	 */
	private static final Logger LOG = LogManager.getLogger("exceptionLog");
	
	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		Result result = new Result();
		StringBuilder sb = new StringBuilder();

		if (ex instanceof BindException) {
			resolverBindException(ex, sb, result);
		} else if (ex instanceof BussinessException) {
			resolverBussinessException(ex, sb, result);
		} else {
			resolverOtherException(ex, sb, result);
		}

		result.setResult(sb);
		LOG.error("程序出现异常：" + sb, ex);

		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache, must-revalidate");
		try {
			response.getWriter().write(JSON.toJSONString(result));
		} catch (IOException e) {
			LOG.error("与客户端通讯异常：" + e.getMessage(), e);
		}
		return new ModelAndView();
	}
    
	/**
     * 处理业务层异常
     */
	private void resolverBussinessException(Throwable e, StringBuilder sb, Result result) {
		BussinessException businessException = (BussinessException) e;
		sb.append(businessException.getBaseEnum().getMsg());
		addResult(result, businessException.getBaseEnum().getCode(), businessException.getBaseEnum().getMsg());
	}

	/**
     * 处理参数绑定异常
     */
	private void resolverBindException(Throwable e, StringBuilder sb, Result result) {
		BindException be = (BindException) e;
		List<FieldError> errorList = be.getBindingResult().getFieldErrors();
		for (FieldError error : errorList) {
			sb.append(error.getObjectName());
			sb.append("对象的");
			sb.append(error.getField());
			sb.append("字段");
			sb.append(error.getDefaultMessage());
		}
		addResult(result, ResponseEnum.PARAM_ERROR.getCode(), ResponseEnum.PARAM_ERROR.getMsg());
	}

	/**
     * 处理其他异常
     */
	private void resolverOtherException(final Throwable e, StringBuilder sb, Result result) {
		sb.append(e.getMessage());
		addResult(result, ResponseEnum.FAILED.getCode(), ResponseEnum.FAILED.getMsg());
	}

	/**
     * 封装code和msg
     */
	private void addResult(Result result, final int code, final String msg) {
		result.setCode(code);
		result.setMsg(msg);
	}
}