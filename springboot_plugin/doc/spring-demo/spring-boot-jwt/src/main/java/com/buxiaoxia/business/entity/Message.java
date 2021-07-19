package com.buxiaoxia.business.entity;

import lombok.Data;

/**
 * Created by xw on 2017/2/21.
 * 2017-02-21 11:40
 */
@Data
public class Message {

	private String message;

	private String code;

	public Message(){
	}

	public Message(String code, String message){
		this.code = code;
		this.message = message;
	}

	public Message(String message){
		this.message = message;
	}
}
