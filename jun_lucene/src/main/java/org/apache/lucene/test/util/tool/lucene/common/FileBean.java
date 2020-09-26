package org.apache.lucene.test.util.tool.lucene.common;

import java.util.Date;

/**
 * 简单文档Bean.
 * 
 * @author Baishui2004
 */
public class FileBean {

	/**
	 * 唯一编号.
	 */
	private String id;

	/**
	 * 标题.
	 */
	private String title;

	/**
	 * 类型.
	 */
	private int type;

	/**
	 * 内容.
	 */
	private String content;

	/**
	 * 原始路径.
	 */
	private String uri;

	/**
	 * 创建/更新时间.
	 */
	private Date time;

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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
