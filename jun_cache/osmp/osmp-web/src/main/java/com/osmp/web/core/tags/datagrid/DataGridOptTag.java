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
public class DataGridOptTag extends TagSupport {
	private static final long serialVersionUID = 1L;

	private String url;

	private String name;

	private String jsName;

	private String filter;

	public String getJsName() {
		return jsName;
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	@Override
	public int doEndTag() throws JspException {
		Tag t = findAncestorWithClass(this, DataGridTag.class);
		// Tag t = this.getParent();
		DataGridTag parent = (DataGridTag) t;
		parent.setDateGridOpts(name, url, jsName, filter);
		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return super.doStartTag();
	}

}
