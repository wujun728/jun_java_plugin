package com.test.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Order implements Serializable {

	private int userId;

	private int orderId;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

}
