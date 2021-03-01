package com.jun.plugin.poi.test.excel.exception;

public class IllegalCellConfigException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IllegalCellConfigException() {
		super();
	}

	public IllegalCellConfigException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public IllegalCellConfigException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalCellConfigException(String message) {
		super(message);
	}

	public IllegalCellConfigException(Throwable cause) {
		super(cause);
	}

}
