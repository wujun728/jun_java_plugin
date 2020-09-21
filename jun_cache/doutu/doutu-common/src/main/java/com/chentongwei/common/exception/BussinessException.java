package com.chentongwei.common.exception;

import com.chentongwei.common.constant.ResponseEnum;

/**
 * 业务异常
 * 
 * @author Tongwei.Chen 2017-5-14 18:18:22
 */
public class BussinessException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private ResponseEnum responseEnum;

	public BussinessException(ResponseEnum responseEnum) {
		super();
		this.responseEnum = responseEnum;
	}

	public ResponseEnum getResponseEnum() {
		return responseEnum;
	}

	public void setResponseEnum(ResponseEnum responseEnum) {
		this.responseEnum = responseEnum;
	}
}
