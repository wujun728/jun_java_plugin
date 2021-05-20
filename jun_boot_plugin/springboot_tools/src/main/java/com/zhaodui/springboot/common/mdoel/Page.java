package com.zhaodui.springboot.common.mdoel;

import java.util.List;

/**
 * 分页对象
 *
 * @author Wujun
 * @param <T>
 */
public class Page<T> {
	private Integer code = 200;
	private String msg = "请求成功";
	private Long total;
	private List<T> data;

	public Page(List<T> data, Long total) {
		this.data = data;
		this.total = total;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}
