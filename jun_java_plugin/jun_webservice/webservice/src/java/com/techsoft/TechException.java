package com.techsoft;

public class TechException extends Exception {
	private static final long serialVersionUID = -1456070014374034056L;

	public TechException() {
		super();
	}

	public TechException(String message) {
		super(message);
	}

	public TechException(String message, Throwable cause) {
		super(message, cause);
	}

	public TechException(Throwable cause) {
		super(cause);
	}

}
