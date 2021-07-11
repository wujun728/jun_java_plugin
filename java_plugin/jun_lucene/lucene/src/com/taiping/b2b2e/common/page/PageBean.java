package com.taiping.b2b2e.common.page;

import java.util.ArrayList;
import java.util.List;


public class PageBean<T>{
	
	private static final long serialVersionUID = -9135374123948501729L;

	private int totalRows; // 记录总数

	private int pageSize = 10;

	private int currentPage; // 当前页

	private int totalPages; // 总页数

	private int startRow; // 开始行
	
	/**
	 * 当前页结果集
	 */
	private List<T> resultList = new ArrayList<T>();

	public PageBean(int _totalRows) {
		totalRows = _totalRows;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		currentPage = 1;
		startRow = 0;
	}
	
	public PageBean(int _totalRows,int _pageSize) {
		totalRows = _totalRows;
		pageSize = _pageSize;
		totalPages = totalRows / pageSize;
		int mod = totalRows % pageSize;
		if (mod > 0) {
			totalPages++;
		}
		currentPage = 1;
		startRow = 0;
	}

	public int getStartRow() {
		return startRow;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void first() {
		currentPage = 1;
		startRow = 0;
	}

	public void previous() {
		if (currentPage == 1) {
			return;
		}
		if(currentPage==0){
			currentPage=1;
			return;
		}
		currentPage--;
		startRow = (currentPage - 1) * pageSize;
	}

	public void next() {
		if (currentPage < totalPages) {
			currentPage++;
		}
		startRow = (currentPage - 1) * pageSize;
	}

	public void last() {
		currentPage = totalPages;
		startRow = (currentPage - 1) * pageSize;
	}

	public void refresh(int _currentPage) {
		currentPage = _currentPage==0?1:_currentPage;
		if (currentPage > totalPages) {
			last();
		}
	}
	
	public void gotoPage(int goPage) {
		if(goPage < 1) 
			goPage = 1;
		else if(goPage > totalPages)
			goPage = totalPages;
		
		currentPage = goPage;
		startRow = (currentPage - 1) * pageSize;
	}
	
	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
}
