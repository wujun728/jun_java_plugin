package com.zandili.demo.drools.pojo;

import java.io.Serializable;

/**
 * 只是个测试
 * 
 * @author Wujun
 * 
 */
public class Message implements Serializable{
 
	private static final long serialVersionUID = -7670667437162623983L;

	private String msg;

	private String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
