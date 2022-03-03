package com.jun.common.json;

import java.io.Serializable;

import com.jun.common.Globarle;

/**
 * Json模型类
 * @author Wujun
 * @createTime   2011-5-8 下午03:35:42
 */
public class JSONModel implements Serializable{
	/**
	 * 总记录数属性名
	 */
	public static final String TOTAL_FIELD = "total";
	/**
	 * 返回Json数组属性名
	 */
	public static final String ROOT_FIELD = "data";
	/**
	 * 访问后台执行成功与否属性名
	 */
	public static final String SUCCESS_FIELD = "success";
	/**
	 * 返回后台返回提示信息属性名
	 */
	public static final String MESSAGE_FIELD = "msg";
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 开始记录索引
	 */
	private transient int start;
	/**
	 * 每页显示条数
	 */
	private transient int limit;
	/**
	 * 执行非Query操作返回的提示信息
	 */
	private transient String message;
	/**
	 * 是否执行成功
	 */
	private boolean success;
	/**
	 * 执行动作类型(CRUD)
	 */
	private transient boolean queryAction;
	/**
	 * 返回的Json字符串
	 */
	private String jsonString;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		if(limit == 0){
			limit = Globarle.PAGESIZE;
		}
		this.limit = limit;
	}
	public String getJsonString() {
		return jsonString;
	}
	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isQueryAction() {
		return queryAction;
	}
	public void setQueryAction(boolean queryAction) {
		this.queryAction = queryAction;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
}
