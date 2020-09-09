package com.github.ghsea.rpc.core;

import java.io.Serializable;

public class RpcResponse implements Serializable {

	private static final long serialVersionUID = 5443134566687425266L;

	protected Throwable cause;

	private String requestId;

	private Object result;

	private RpcResponseFuture future;

	public RpcResponse() {
		this.future = new DefaultRpcResponseFuture();
	}

	public RpcResponseFuture getFuture() {
		return future;
	}

	public void setFuture(RpcResponseFuture future) {
		this.future = future;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

}
