package com.erp.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable {
	private static final long serialVersionUID = -1748602382963711884L;
	private Integer userId;
	private String account;

	public ShiroUser(Integer userId, String account) {
		super();
		this.userId = userId;
		this.account = account;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	public String toString() {
		return account;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}
}
