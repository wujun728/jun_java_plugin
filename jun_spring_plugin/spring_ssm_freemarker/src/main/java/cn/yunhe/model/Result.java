package cn.yunhe.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 返回统一JSON结构
 * 
 * @author Wujun
 *
 */
@JsonInclude(Include.NON_NULL)
public class Result {
	/**
	 * 业务代码
	 */
	private int code;
	/**
	 * 业务消息
	 */
	private String message;
	/**
	 * 业务数据
	 */
	private List<Object> data;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Object> getData() {
		return data;
	}

	public void setData(List<Object> data) {
		this.data = data;
	}
}
