/*   
 * Project: OSMP
 * FileName: ExecutorWrapper.java
 * version: V1.0
 */
package com.osmp.web.core.tags.datagrid;

/**
 * 数据列表自定义标签 行操作子标签
 * 
 * @author wangkaiping
 * @version V1.0, 2013-4-24 下午05:01:34
 */
public class DataGridOpt {

	/**
	 * 操作项名称
	 */
	private String name;

	/**
	 * 操作项提交的URL
	 */
	private String url;

	private String jsName;

	private String filter;

	public DataGridOpt(String name, String url, String jsName, String filter) {
		super();
		this.name = name;
		this.url = url;
		this.jsName = jsName;
		this.filter = filter;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getJsName() {
		return jsName;
	}

	public void setJsName(String jsName) {
		this.jsName = jsName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
