package com.buxiaoxia.system.exception;

public class AuthorizationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AuthorizationException() {
	}

	public AuthorizationException(String message) {
		super(message);
	}

	public AuthorizationException(Throwable cause) {
		super(cause);
	}

	public AuthorizationException(String message, Throwable cause) {
		super(message, cause);
	}

}
