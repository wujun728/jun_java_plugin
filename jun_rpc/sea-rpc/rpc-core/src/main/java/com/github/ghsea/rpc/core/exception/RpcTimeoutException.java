package com.github.ghsea.rpc.core.exception;

public class RpcTimeoutException extends RpcException {

	private static final long serialVersionUID = -6582678132662866710L;

	public RpcTimeoutException(String cause) {
		super(cause);
	}

	public RpcTimeoutException() {
		super();
	}

	/**
	 * 
	 * @param time
	 *            所花费的时间
	 */
	public RpcTimeoutException(long time) {
		super(String.format("Used time %d miliseconds", time));
	}

}
