package com.github.ghsea.rpc.core.exception;

public class RpcException extends RuntimeException {
	private static final long serialVersionUID = 5567223866591480293L;

	public RpcException(Throwable cause) {
		super(cause);
	}

	public RpcException(String cause) {
		super(cause);
	}

	public RpcException() {

	}
}
