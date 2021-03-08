package com.sam.demo.rabbitmq.message;

import java.io.Serializable;
import java.util.Date;

public class SendData2 implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4927337268812487149L;
	private int id;
	private String content;
	private Date time;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
