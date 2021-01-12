package com.itheima.domain;
//粗粒度：表的定义应该粗。少
//细粒度：类的定义尽量的细。多
public class Person {
	private int id;
	private String name;
	private IdCard idcard;
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
	public IdCard getIdcard() {
		return idcard;
	}
	public void setIdcard(IdCard idcard) {
		this.idcard = idcard;
	}
	
}
