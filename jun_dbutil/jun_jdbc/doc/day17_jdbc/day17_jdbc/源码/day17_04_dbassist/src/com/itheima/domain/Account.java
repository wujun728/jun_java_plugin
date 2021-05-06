package com.itheima.domain;
//一般：实体类的字段名和数据库表的字段名保持一致
//约定优于编码
public class Account {
	private int id;
	private String name;
	private float money;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public float getMoney() {
		return money;
	}
	public void setMoney(float money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "Account [id=" + id + ", name=" + name + ", money=" + money
				+ "]";
	}
	
}
