package com.github.ghsea.rpc.core.message;

import java.io.Serializable;

import com.alibaba.fastjson.JSON;

/**
 * 服务端返回的消息
 * 
 * @author ghsea 2017-4-9下午6:04:17
 */
public class RpcResponseMsg implements Serializable {

	private static final long serialVersionUID = -3105337152848751833L;

	protected Throwable cause;

	private String requestId;

	private Object result;

	public RpcResponseMsg() {

	}

	public RpcResponseMsg(String requestId) {
		this.requestId = requestId;
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

	public String toString() {
		return JSON.toJSONString(this);
	}

}
