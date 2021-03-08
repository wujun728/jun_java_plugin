package com.chentongwei.common.entity;

import java.io.Serializable;

/**
 * @author Wujun
 * @Project tucaole
 * @Description: 需要分页的IO，其他需要分页的IO可以继承此IO
 */
public class Page implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final int DEFAULT_PAGENUM = 1;
	private static final int DEFAULT_PAGESIZE = 10;

	//第几页
	private int pageNum;
	//每页条数
	private int pageSize;

	public Page() {
		if (pageNum <= 0) {
			this.pageNum = DEFAULT_PAGENUM;
		}
		if (pageSize <= 0) {
			this.pageSize = DEFAULT_PAGESIZE;
		}
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
		if (pageNum == Integer.MAX_VALUE) {
			this.pageNum = 0;
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize == Integer.MAX_VALUE) {
			this.pageSize = 0;
		}
	}
}