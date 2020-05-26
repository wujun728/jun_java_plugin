package com.bing.excel.exception;

/**
 * @author shizhongtao
 *
 */
public class InitializationException extends RuntimeException {

	public InitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InitializationException(String message) {
		super(message);
	}

	public InitializationException(Throwable cause) {
		super(cause);
	}
	

}
