package com.jun.common;

/**
 * @author Wujun
 * @createTime   2011-11-19 下午09:42:14
 */
public class PageModel {
	/**
	 * 当前页码
	 */
    private int currentPage;
    /**
     * 每页显示条数
     */
    private int pageSize;
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
	
}
