package org.iherus.codegen.utils;

public class HttpConnectionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4357540105881789619L;

	public HttpConnectionException() {
		super();

	}

	public HttpConnectionException(String message) {
		super(message);
	}

	public HttpConnectionException(Throwable cause) {
		super(cause);
	}

	public HttpConnectionException(String message, Throwable cause) {
		super(message, cause);
	}

}
