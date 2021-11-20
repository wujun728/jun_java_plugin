package com.caland.core.query;

import java.io.Serializable;

/**
 * @author hj
 */
public class BaseQuery implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_SIZE = 10;
	protected int pageSize = DEFAULT_SIZE;
	protected int startRow;//起始行
	protected int endRow;//结束行(闭合)
	protected int page = 1;
	protected String fields;

	public BaseQuery(){};
	public BaseQuery(int page, int pageSize){
		this.page = page;
		this.pageSize = pageSize;
		this.startRow = (page-1)*this.pageSize;
		this.endRow= this.startRow + this.pageSize - 1;
	}
	
	public int getStartRow() {
		return startRow;
	}
	public BaseQuery setStartRow(int startRow) {
		this.startRow = startRow;
		return this;
	}
	public int getEndRow() {
		return endRow;
	}
	public BaseQuery setEndRow(int endRow) {
		this.endRow = endRow;
		return this;
	}
	public BaseQuery setPage(int page) {
		this.page = page;
		this.startRow = (page-1)*this.pageSize;
		this.endRow= this.startRow + this.pageSize - 1;
		return this;
	}
	public int getPageSize() {
		return pageSize;
	}
	public BaseQuery setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if(pageSize != DEFAULT_SIZE && page > 0){
			this.startRow = (page-1)*this.pageSize;
			this.endRow= this.startRow + this.pageSize - 1;
		}
		return this;
	}
	public int getPage() {
		return page;
	}
	
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
	
}
