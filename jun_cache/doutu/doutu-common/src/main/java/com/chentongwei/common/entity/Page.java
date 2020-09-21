package com.chentongwei.common.entity;

import java.io.Serializable;

/**
 * 需要分页的IO，其他需要分页的IO可以继承此IO
 * 
 * @author TongWei.Chen 2017-5-14 18:14:27
 */
public class Page implements Serializable {

	private static final long serialVersionUID = 1L;

	//第几页
	private int pageNum;
	//每页条数
	private int pageSize;

	public Page() {
		if(this.pageNum < 1) {
			this.pageNum = 1;
		}
		this.pageSize = 10;
	}
	
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}