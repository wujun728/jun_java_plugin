package com.osmp.web.utils;

import java.util.List;

/**
 * 分页实体类
 * 
 * @author wangkaiping
 * @version 1.0 2013-4-3
 */
public class Pagination<T> {

	public Pagination() {

	}

	public Pagination(int curPage, int pageSize) {
		super();
		this.pageSize = pageSize;
		this.curPage = curPage;
	}

	/**
	 * 总条数
	 */
	private int total;

	/**
	 * 每页显示条数
	 */
	private int pageSize;

	/**
	 * 当前页
	 */
	private int curPage;

	/**
	 * 数据
	 */
	private List<T> list;

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalPage() {
		return (total + pageSize - 1) / pageSize;
	}

	/**
	 * 
	 * @return 第一页
	 */
	public int getFirstPage() {
		return 1;
	}

	/**
	 * 
	 * @return 最后一页
	 */
	public int getButtomPage() {
		return this.getTotalPage();
	}

	/**
	 * 
	 * @return 下一页
	 */
	public int nextPage() {
		return this.curPage + 1 > this.getTotalPage() ? this.getTotalPage() : this.curPage + 1;
	}

	/**
	 * 
	 * @return 上一页
	 */
	public int prevPage() {
		return this.curPage - 1 < 1 ? 1 : this.curPage - 1;
	}
}