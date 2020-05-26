package com.bing.excel.writer.exception;

public class ExcelOutException extends RuntimeException {

	public ExcelOutException(String message, Throwable cause) {
		super(message, cause);
	}

	public ExcelOutException(String message) {
		super(message);
	}

	public ExcelOutException(Throwable cause) {
		super(cause);
	}

}
