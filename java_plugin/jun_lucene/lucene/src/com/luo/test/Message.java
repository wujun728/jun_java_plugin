package com.luo.test;

import org.apache.solr.client.solrj.beans.Field;

public class Message {
	private String id;
	private String title;
	private String[] content;//数组可以添加内容和附件
	
	public Message() {
		super();
	}
	public Message(String id, String title, String[] content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}
	public String getId() {
		return id;
	}
	@Field
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	@Field("title")//当和schema.xml中的Filed名字不一样，需要写出来
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getContent() {
		return content;
	}
	@Field("")//此处是数组，是多值，相应的content应该设置multiValued="true"
	public void setContent(String[] content) {
		this.content = content;
	}
}
