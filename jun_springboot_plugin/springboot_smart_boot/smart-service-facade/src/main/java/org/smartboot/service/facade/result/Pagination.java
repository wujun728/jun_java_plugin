package org.smartboot.service.facade.result;

import java.util.List;

public class Pagination<T> extends ToString {
	/** 数据总量 */
	private long total;
	/** 当前页码 */
	private int page;
	/** 单页数据量 */
	private int pageSize;

	private List<T> data;

	public Pagination(long total, int page, int pageSize) {
		super();
		this.total = total;
		this.page = page;
		this.pageSize = pageSize;
	}

	/**
	 * Getter method for property <tt>total</tt>.
	 *
	 * @return property value of total
	 */
	public final long getTotal() {
		return total;
	}

	/**
	 * Setter method for property <tt>total</tt>.
	 *
	 * @param total
	 *            value to be assigned to property total
	 */
	public final void setTotal(long total) {
		this.total = total;
	}

	/**
	 * Getter method for property <tt>page</tt>.
	 *
	 * @return property value of page
	 */
	public final int getPage() {
		return page;
	}

	/**
	 * Setter method for property <tt>page</tt>.
	 *
	 * @param page
	 *            value to be assigned to property page
	 */
	public final void setPage(int page) {
		this.page = page;
	}

	/**
	 * Getter method for property <tt>pageSize</tt>.
	 *
	 * @return property value of pageSize
	 */
	public final int getPageSize() {
		return pageSize;
	}

	/**
	 * Setter method for property <tt>pageSize</tt>.
	 *
	 * @param pageSize
	 *            value to be assigned to property pageSize
	 */
	public final void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * Getter method for property <tt>data</tt>.
	 *
	 * @return property value of data
	 */
	public final List<T> getData() {
		return data;
	}

	/**
	 * Setter method for property <tt>data</tt>.
	 *
	 * @param data
	 *            value to be assigned to property data
	 */
	public final void setData(List<T> data) {
		this.data = data;
	}

}
