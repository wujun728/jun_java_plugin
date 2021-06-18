package com.itheima.exception;

public class UserHasExistException extends Exception {

	public UserHasExistException() {
	}

	public UserHasExistException(String message) {
		super(message);
	}

	public UserHasExistException(Throwable cause) {
		super(cause);
	}

	public UserHasExistException(String message, Throwable cause) {
		super(message, cause);
	}

}
