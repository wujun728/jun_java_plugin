package com.luo.vo;

import java.util.Date;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 索引Bean
 * @author 罗辉, @date:Aug 2, 2013
 *
 */
public class IndexField {
	
	private String id;//或者直接在属性上加@Field
	private String title;
	private String summary;
	private String content;
	private Date addTime;
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
	@Field
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSummary() {
		return summary;
	}
	@Field("description")
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getContent() {
		return content;
	}
	@Field
	public void setContent(String content) {
		this.content = content;
	}
	public Date getAddTime() {
		return addTime;
	}
	@Field("last_modified")//千万要注意加在setter上，否则就会报Invalid setter method. Must have one and only one parameter
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
}
