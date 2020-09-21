/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags.datagrid;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-24 下午04:31:25
 */
public class DataGridToolBar extends TagSupport {
	private static final long serialVersionUID = 1L;

	public DataGridToolBar() {
	};

	public DataGridToolBar(String title, String jsName, String icon) {
		this.title = title;
		this.jsName = jsName;
		this.icon = icon;
	};

	/**
	 * 按钮名称
	 */
	private String title;

	/**
	 * 按钮图标
	 */
	private String icon;

	/**
	 * 按钮执行的js方法名称
	 */
	private String jsName;

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getJsName() {
		return jsName;
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public int doEndTag() throws JspException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		DataGridTag parent = (DataGridTag) t;
		parent.setDateGridToolBar(title, jsName, icon);
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

}
