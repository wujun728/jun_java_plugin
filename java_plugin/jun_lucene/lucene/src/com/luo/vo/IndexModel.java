package com.luo.vo;

import java.util.Date;

/**
 * lucene模型,用于在页面展示
 * @author 罗辉, @date:2013-8-1
 *
 */
public class IndexModel {
	private String id;
	private String title;
	private String summary;
	private String content;
	private Date addTime;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
