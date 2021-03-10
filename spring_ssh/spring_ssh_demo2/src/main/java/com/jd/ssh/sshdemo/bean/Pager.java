package com.jd.ssh.sshdemo.bean;

import java.util.List;

import com.jd.ssh.sshdemo.bean.EnumManage.OrderType;

/**
 * Bean类 - 分页
 * 当前页码	默认为	1；	设为0则查询所有
 * 每页记录数 默认为	10
 * 排序字段 	默认为	createDate
 * 排序方式	默认为	OrderType.desc;// 
 * ============================================================================
 * 版权所有 2013 qshihua。
 * 
 * @author qshihua
 * @version 0.1 2013-1-16
 * ============================================================================
 */

public class Pager {

	/**
	 * new一个Page对象
	 * 页码		默认为	1
	 * 记录数	默认为	10
	 * 排序字段	默认为	createDate
	 * 排序方式	默认为	desc
	 */
	public Pager() {
		super();
		this.pageNumber = 1;
	}

	/**
	 * new一个Page对象
	 * @param pageNumber 
	 * 					页数（0 则查询所有）
	 * 记录数	默认为	10
	 * 排序字段	默认为	createDate
	 * 排序方式	默认为	desc
	 */
	public Pager(Integer pageNumber) {
		super();
		this.pageNumber = pageNumber;
	}

	public static final Integer MAX_PAGE_SIZE = 500;// 每页最大记录数限制

//	private Integer pageNumber = 1;// 当前页码
	private Integer pageNumber;// 当前页码
	private Integer pageSize = 10;// 每页记录数
	private Integer totalCount = 0;// 总记录数
	private Integer pageCount = 0;// 总页数
	private String property;// 查找属性名称
	private String keyword;// 查找关键字
	private String orderBy = "createDate";// 排序字段
	private OrderType orderType = OrderType.desc;// 排序方式
	private List<?> list;// 数据List

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		if (pageNumber < 0) {
			pageNumber = 1;
		}
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		if (pageSize < 1) {
			pageSize = 1;
		} else if(pageSize > MAX_PAGE_SIZE) {
			pageSize = MAX_PAGE_SIZE;
		}
		this.pageSize = pageSize;
	}
	
	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public Integer getPageCount() {
		pageCount = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			pageCount ++;
		}
		return pageCount;
	}

	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	
	public OrderType getOrderType() {
		return orderType;
	}

	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}