package com.jun.plugin.lucene.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 排序规则
 * @author gaojun.zhou
 * @date 2016年9月2日
 */
public enum SortType {
	
	/**
	 * 默认,评分排序,系统自己决定
	 */
	DEFAULT(0,"默认"),
	/**
	 * 时间降序,新品排在前面
	 */
	DATE_DSEC(9,"时间降序"),
	/**
	 * 销量升序
	 */
	SALES_ASC(10,"销量升序"),
	/**
	 * 销量降序
	 */
	SALES_DESC(19,"销量降序"),
	/**
	 * 价格升序
	 */
	PRICE_ASC(20,"价格升序"),
	/**
	 * 价格降序
	 */
	PRICE_DESC(29,"价格降序");

	private int sort ;
	
	private String note;
	
	private SortType(int sort, String note) {
		this.sort = sort;
		this.note = note;
	}
	public int getSort() {
		return sort;
	}
	public void setSort(int sort) {
		this.sort = sort;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	/**
	 * 存储Map
	 */
	private static Map<Integer,SortType> map = new HashMap<Integer, SortType>();
	static{
      for(SortType sortType : SortType.values()){
    	  map.put(sortType.getSort(),sortType);
      }
	}
	
	public static SortType getSortType(Integer sort){
		return map.get(sort);
	}
}
