package org.smartboot.dal.customize.query;

public class Page {
	/** 当前页码 */
	private int page;
	/** 单页大小 */
	private int pageSize;

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

	public final int getRows() {
		return pageSize;
	}

	public final int getOffset() {
		return (page - 1) * pageSize;
	}
}
