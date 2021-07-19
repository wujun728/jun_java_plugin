package com.buxiaoxia.system.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(AuthorizationException.class)
	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ResponseBody
	public String handleAuthorizationExceptionExceptionError(RuntimeException ex) {
		ex.printStackTrace();
		return "{\"message\":\"" + ex.getMessage() + "\"}";
	}

}  