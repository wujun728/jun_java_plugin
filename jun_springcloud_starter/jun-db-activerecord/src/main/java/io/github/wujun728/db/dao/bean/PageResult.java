package io.github.wujun728.db.dao.bean;


import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页返回通用类
 * 
 * @author 彭佳佳
 * @param <T> 結果類型
 */
public class PageResult<T> implements Serializable {
	/**
	 * 总数量 
	 */
	private Integer total;
	/**
	 * 返回对象类型列表
	 */
	private List<T> list;

	public PageResult() {
		
	}

	public PageResult(List<T> list, Integer total) {
		this.total = total;
		this.list = list;
	}

	/**
	 * 创建一个空的分页结果集
     * @author 周宁
	 * @return PageResult
	 * @version 1.0
	 */
	public static PageResult emptyPageResult(){
	    return new PageResult(Collections.EMPTY_LIST,0);
    }

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	@Override
	public String toString() {
		return "PageResult{" +
				"total=" + total +
				", list=" + list +
				'}';
	}
}
