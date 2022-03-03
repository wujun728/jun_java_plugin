package com.mis.httpModel;

import java.math.BigDecimal;

public class Portal implements java.io.Serializable {

	private String id;
	private String title;
	private String src;
	private BigDecimal height;
	private String closable;
	private String collapsible;
	private BigDecimal seq;

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

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getClosable() {
		return closable;
	}

	public void setClosable(String closable) {
		this.closable = closable;
	}

	public String getCollapsible() {
		return collapsible;
	}

	public void setCollapsible(String collapsible) {
		this.collapsible = collapsible;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}
