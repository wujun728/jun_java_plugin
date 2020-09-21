package com.yzm.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yzm.vo.ResponseResult;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(value = { Exception.class, RuntimeException.class, IllegalArgumentException.class })
	@ResponseBody
	public ResponseResult defaultErrorHandler(HttpServletRequest req, Exception e) {

		ResponseResult.Builder rr = new ResponseResult.Builder().fail();
		rr.message(e.getMessage());
		if (e instanceof org.springframework.web.servlet.NoHandlerFoundException) {
			rr.code(404);
		} else {
			if (e instanceof org.springframework.web.HttpRequestMethodNotSupportedException
					|| e instanceof org.springframework.web.HttpMediaTypeNotAcceptableException
					|| e instanceof org.springframework.web.util.NestedServletException) {
				rr.code(500);
			} else {

				rr.code(500);
			}

		}
		return rr.build();
	}

}
