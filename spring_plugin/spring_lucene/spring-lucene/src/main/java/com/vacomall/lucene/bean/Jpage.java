package com.vacomall.lucene.bean;


import java.io.Serializable;
import java.util.List;
/**
 * 分页插件
 * @author Administrator
 *
 * @param <T>
 */
public class Jpage<T> implements Serializable {
	
	private static final long serialVersionUID = -5395997221963176643L;
	
	private List<T> list;				// list result of this page
	private int pageNumber	= 1;				// page number
	private int pageSize	= 100;				// result amount of this page
	private int totalPage;				// total page
	private int totalRow;				// total row
	public List<T> getList() {
		return list;
	}
	public void setList(List<T> list) {
		this.list = list;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public Jpage() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Jpage(int pageNumber, int pageSize) {
		super();
		this.pageNumber = pageNumber;
		this.pageSize = pageSize;
	}
	
}



