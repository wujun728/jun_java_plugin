package com.mis.httpModel;

import java.math.BigDecimal;

//import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * 菜单模型
 * 
 * @author Wujun
 * 
 */
//@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
public class Menu implements java.io.Serializable {

	private String id;
	private String text;
	private String iconCls;
	private String state;
	private String checked;
	private String src;
	private BigDecimal seq;

	private String parentId;
	private String parentText;

	public String getParentText() {
		return parentText;
	}

	public void setParentText(String parentText) {
		this.parentText = parentText;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public BigDecimal getSeq() {
		return seq;
	}

	public void setSeq(BigDecimal seq) {
		this.seq = seq;
	}

}
