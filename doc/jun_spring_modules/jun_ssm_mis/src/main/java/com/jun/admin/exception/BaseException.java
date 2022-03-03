package com.jun.admin.exception;

/**
 * 基础平台异常信息 caimf
 */
public class BaseException extends Exception {

	private static final long serialVersionUID = -6822091028165261873L;

	public BaseException() {}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}
}