package com.jun.plugin.lucene.bean;

import com.jun.plugin.lucene.enums.SortType;

/**
 * Lucen 查询条件
 * @author Administrator
 *
 */
public class QueryConfig<T> {

	/**
	 * 查询关键词
	 */
	private String kw;
	/**
	 * 排序策略
	 */
	private SortType sortType;
	/**
	 * 分页对象
	 */
	private Jpage<T> jpage;
	/**
	 * 最小金额
	 */
	private Float min;
	/**
	 * 最大金额
	 */
	private Float max;
	/**
	 * 筛选策略
	 */
	private QueryFilter queryFilter;
	
	public String getKw() {
		return kw;
	}
	public void setKw(String kw) {
		this.kw = kw;
	}
	public SortType getSortType() {
		return sortType;
	}
	public void setSortType(SortType sortType) {
		this.sortType = sortType;
	}
	public Jpage<T> getJpage() {
		return jpage;
	}
	public void setJpage(Jpage<T> jpage) {
		this.jpage = jpage;
	}
	public Float getMin() {
		return min;
	}
	public void setMin(Float min) {
		this.min = min;
	}
	public Float getMax() {
		return max;
	}
	public void setMax(Float max) {
		this.max = max;
	}
	public QueryFilter getQueryFilter() {
		return queryFilter;
	}
	public void setQueryFilter(QueryFilter queryFilter) {
		this.queryFilter = queryFilter;
	}
	
	
	
}
