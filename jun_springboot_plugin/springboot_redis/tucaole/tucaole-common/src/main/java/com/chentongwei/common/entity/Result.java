package com.chentongwei.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 统一返回给客户端的结果对象
 */
@Data
public class Result implements Serializable {

	private static final long serialVersionUID = 1L;
	
	//状态 0：失败 1：成功
	private int code;
	//提示消息
	private String msg;
	//返回内容
	private Object result;
}
