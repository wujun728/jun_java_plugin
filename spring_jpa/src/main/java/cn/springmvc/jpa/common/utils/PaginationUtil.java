package cn.springmvc.jpa.common.utils;

import java.util.Collection;

/**
 * 通用分页对象
 * @author Vincent.wang
 *
 */
public class PaginationUtil<T> {

	// 总共的数据量
	private int total;

	// 每页显示多少条
	private int pageSize;

	// 当前是第几页
	private int index;

	// 数据
	private Collection<T> data;

	public PaginationUtil() {
		this.pageSize = 50;
	}

	public PaginationUtil(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public int getFirstResult() {
		return pageSize * (index - 1);
	}

	public int getMaxResults() {
		return pageSize * index - 1;
	}

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

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Collection<T> getData() {
		return data;
	}

	public void setData(Collection<T> data) {
		this.data = data;
	}

	public int getTotlePage() {
		return total / pageSize + (total % pageSize == 0 ? 0 : 1);
	}

}
