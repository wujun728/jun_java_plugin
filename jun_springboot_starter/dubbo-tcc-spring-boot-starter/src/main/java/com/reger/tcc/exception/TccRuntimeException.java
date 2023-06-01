package com.reger.tcc.exception;

public class TccRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	/**
	 * 该异常是否需要回滚
	 */
	private boolean rollback = true;

	public TccRuntimeException(Throwable cause) {
		super(cause);
	}
	public TccRuntimeException(Throwable cause,boolean rollback) {
		super(cause);
		this.rollback=rollback;
	}

	public boolean isRollback() {
		return rollback;
	}
}