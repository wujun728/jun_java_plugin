/*   
 * Project: OSMP
 * FileName: ExceptionHandleProvider.java
 * version: V1.0
 */
package com.osmp.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.ext.ExceptionMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 异常处理
 * 
 * @author heyu
 *
 */
public class ExceptionHandleProvider implements ExceptionMapper<Throwable> {

	private Logger logger = LoggerFactory.getLogger(ExceptionHandleProvider.class);

	public Response toResponse(Throwable ex) {
		logger.error(getStackTrace(ex));
		ResponseBuilder rb = Response.status(Response.Status.INTERNAL_SERVER_ERROR);
		rb.type("application/json;charset=UTF-8");
		rb.entity("{\"code\":0}");
		return rb.build();
	}

	private String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}

}
