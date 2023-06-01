package com.springboot.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象。
 * 
 * @param <T>
 *            分页对象中包含内容的对象类型
 */
@SuppressWarnings("serial")
public class Page<T> implements Serializable {

	/** 是否第一页 */
	private Boolean first = true;
	/** 是否最后一页 */
	private Boolean last = true;
	/** 总页数 */
	private Integer pageCount = 0;
	/** 总记录数 */
	private Integer count = 0;
	/** 下一页页码 */
	private Integer next = 1;
	/** 上一页页码 */
	private Integer previous = 1;
	/** 每页记录数 */
	private Integer pageSize = 10;
	/** 当前页码 */
	private Integer pageNumber = 1;
	/** 分页记录集合 */
	private List<T> contents = new ArrayList<T>();
	/** 页数序号 */
	private List<Integer> indexs = new ArrayList<Integer>();

	private Integer position = 0;

	private String orderBys = "";

	/**
	 * default constructor
	 */
	public Page() {
	}

	/**
	 * 初始化一个新的分页对象，该构造方法通常用于生成一个空的分页对象。
	 * 
	 * @param pageSize
	 *            每页记录数
	 */
	public Page(Integer pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 通过指定记录总数、当前页数、每页记录数来构造一个分页对象。
	 * 
	 * 
	 * @param recordCount
	 *            记录总数
	 * @param pageNo
	 *            当前页数
	 * @param pageSize
	 *            每页记录数
	 */
	public Page(Integer recordCount, Integer pageNo, Integer pageSize) {
		count = recordCount;
		this.pageSize = pageSize <= 0 ? 10 : pageSize;
		pageCount = count % this.pageSize > 0 ? count / this.pageSize + 1 : count / this.pageSize;
		// number = pageCount < pageNo ? pageCount : pageNo;
		pageNumber = pageNo < 0 ? 0 : pageNo;
		first = pageNumber <= 1;
		previous = pageNumber <= 1 ? pageNumber : pageNumber - 1;
		last = pageNumber >= pageCount;
		next = pageNumber >= pageCount ? pageNumber : pageNumber + 1;
		contents = new ArrayList<T>();
		indexs = new ArrayList<Integer>();
		for (int i = 1; i <= pageCount; i++) {
			indexs.add(i);
		}
	}

	public Boolean getFirst() {
		return first;
	}

	public void setFirst(Boolean first) {
		this.first = first;
	}

	public Boolean getLast() {
		return last;
	}

	public void setLast(Boolean last) {
		this.last = last;
	}

	public Integer getPageCount() {
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public Integer getNext() {
		return next;
	}

	public void setNext(Integer next) {
		this.next = next;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize == null ? 10 : pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getPrevious() {
		return previous;
	}

	public void setPrevious(Integer previous) {
		this.previous = previous;
	}

	public List<T> getContents() {
		return contents;
	}

	public void setContents(List<T> contents) {
		this.contents = contents;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public List<Integer> getIndexs() {
		return indexs;
	}

	public void setIndexs(List<Integer> indexs) {
		this.indexs = indexs;
	}

	public Integer getPosition() {
		return position == null ? 0 : position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public String getOrderBys() {
		return orderBys;
	}

	public void setOrderBys(String orderBys) {
		this.orderBys = orderBys;
	}
}
