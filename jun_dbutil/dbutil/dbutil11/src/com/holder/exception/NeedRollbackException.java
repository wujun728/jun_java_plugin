package com.holder.exception;

public class NeedRollbackException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7230071841869805029L;

	public NeedRollbackException() {

	}

	public NeedRollbackException(Throwable e) {
		super(e);
	}
}
