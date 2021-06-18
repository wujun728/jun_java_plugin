package com.jun.web.biz.entity;

import java.util.Date;

public class Orders {
	private int id; //-- ����
	private int table_id;//  -- ����� �������
	private Date orderDate;//-- �µ�����
	private double totalPrice;//-- �������в���Ҫ���ܽ��
	private int orderStatus;//
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTable_id() {
		return table_id;
	}
	public void setTable_id(int table_id) {
		this.table_id = table_id;
	}
	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public int getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(int orderStatus) {
		this.orderStatus = orderStatus;
	}
	@Override
	public String toString() {
		return "Orders [id=" + id + ", table_id=" + table_id + ", orderDate="
				+ orderDate + ", totalPrice=" + totalPrice + ", orderStatus="
				+ orderStatus + "]";
	}

}