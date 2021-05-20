/*
 * Copyright 2015-2016 RonCoo(http://www.roncoo.com) Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roncoo.jui.common.util.jui;

import java.io.Serializable;
import java.util.List;

/**
 * 数据分页组件
 * 
 * @author Wujun
 * @param <T>
 */
public class Page<T extends Serializable> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 当前分页的数据集
	 */
	private List<T> list;

	/**
	 * 总记录数
	 */
	private int totalCount;

	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 当前页
	 */
	private int currentPage;

	/**
	 * 每页记录数
	 */
	private int numPerPage;

	/**
	 * 排序字段
	 */
	private String orderField;

	/**
	 * 排序方式：asc or desc
	 */
	private String orderDirection;
	
	public Page() {
	}

	/**
	 * 构造函数
	 * 
	 * @param totalCount
	 *            总记录数
	 * @param totalPage
	 *            总页数
	 * @param pageCurrent
	 * @param pageSize
	 * @param list
	 */
	public Page(int totalCount, int totalPage, int currentPage, int numPerPage, List<T> list) {
		this.totalCount = totalCount;
		this.totalPage = totalPage;
		this.currentPage = currentPage;
		this.numPerPage = numPerPage;
		this.list = list;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNumPerPage() {
		return numPerPage;
	}

	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}

	public String getOrderField() {
		return orderField;
	}

	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	@Override
	public String toString() {
		return "Page [list=" + list + ", totalCount=" + totalCount + ", totalPage=" + totalPage + ", currentPage=" + currentPage + ", numPerPage=" + numPerPage + ", orderField=" + orderField + ", orderDirection=" + orderDirection + "]";
	}

}
