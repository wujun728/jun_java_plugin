package com.holder.exception;

public class ConnectionNeededException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3946497825733577662L;

	public ConnectionNeededException() {
		this(
				" There is non connection set into DBContextHolder , please check your code!");
	}

	public ConnectionNeededException(String msg) {
		super();
	}

}
