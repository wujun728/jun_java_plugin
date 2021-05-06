package com.itheima.exception;

public class IdIsNullException extends Exception {

	public IdIsNullException() {
	}

	public IdIsNullException(String message) {
		super(message);
	}

	public IdIsNullException(Throwable cause) {
		super(cause);
	}

	public IdIsNullException(String message, Throwable cause) {
		super(message, cause);
	}

}
