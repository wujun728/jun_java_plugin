package com.jun.common;

import com.jun.common.page.PageData;




/**
 * @author Wujun
 * @createTime   2011-3-27
 */
public abstract class GerneralVO {
	protected int currentPage;                //当前页
	protected int pageSize;                   //每页大小
	 //分页查询结果
	protected PageData items;

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public PageData getItems() {
		return items;
	}

	public void setItems(PageData items) {
		this.items = items;
	}
}
