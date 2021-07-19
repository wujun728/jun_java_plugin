package com.buxiaoxia.utils.exception;

public class UtilException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public UtilException() {
	}

	public UtilException(String message) {
		super(message);
	}

	public UtilException(Throwable cause) {
		super(cause);
	}

	public UtilException(String message, Throwable cause) {
		super(message, cause);
	}

}

