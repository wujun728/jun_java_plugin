package org.typroject.tyboot.core.restful.utils;

import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.typroject.tyboot.core.foundation.context.RequestContext;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <pre>
 *  Tyrest
 *  File: ResponseHelper.java
 * 
 *  Tyrest, Inc.
 *  Copyright (C): 2016
 * 
 *  Description:
 *  TODO
 * 
 *  Notes:
 *  $Id: ResponseHelper.java  Tyrest\magintrursh $ 
 * 
 *  Revision History
 *  &lt;Date&gt;,			&lt;Who&gt;,			&lt;What&gt;
 *  2016年11月1日		magintrursh		Initial.
 *
 * </pre>
 */
public class ResponseHelper {

	public static <T> ResponseModel<T> buildResponse(T t) {
		ResponseModel<T> response = new ResponseModel<>();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		response.setResult(t);
		response.setDevMessage("SUCCESS");
		response.setStatus(HttpStatus.OK.value());
		response.setTraceId(RequestContext.getTraceId());
		response.setPath(request.getServletPath());
		return response;
	}
}

/*
 * $Log: av-env.bat,v $
 */