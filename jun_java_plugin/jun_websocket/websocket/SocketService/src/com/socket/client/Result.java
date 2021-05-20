package com.socket.client;

public class Result {
	
	private String msg;
	
	private Object value;
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * set the value from server
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}
	
	/**
	 * get the value from server.
	 * @return
	 */
	public Object getValue(){
		return this.value;
	}
}
