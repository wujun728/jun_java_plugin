package com.lmy.demo.pojo;

import java.io.Serializable;

public class PostPojo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int index;
	private int userId;
	private int id;
	private String title;
	private String body;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "PostPojo [index=" + index + ", userId=" + userId + ", id=" + id
				+ ", title=" + title + ", body=" + body + "]";
	}

}
