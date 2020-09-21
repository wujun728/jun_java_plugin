package com.chentongwei.common.entity;

import java.io.Serializable;

/**
 * 统一返回给客户端的结果对象
 * 
 * @author TongWei.Chen 2017-5-14 18:14:52
 */
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//状态 0：失败 1：成功
	private int code;
	//提示消息
	private String msg;
	//返回内容
	private Object result;
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
	
}
