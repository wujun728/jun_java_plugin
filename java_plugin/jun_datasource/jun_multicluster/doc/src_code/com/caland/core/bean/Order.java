package com.caland.core.bean;

import java.util.*;
import java.io.Serializable;

/**
 * 
 * @author lixu
 * @Date [2014-3-28 下午04:38:53]
 */
public class Order implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String no;
	private String name;
	private Double price;
	private Integer userId;

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String toString() {
		return "Order [id=" + id + ",no=" + no + ",name=" + name + ",price=" + price + ",userId=" + userId + "]";
	}
}
