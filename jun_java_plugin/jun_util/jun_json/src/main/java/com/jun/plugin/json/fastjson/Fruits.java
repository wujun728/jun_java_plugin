package com.jun.plugin.json.fastjson;


import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;

public class Fruits {
	private String kinds;	//种类
	private Integer num;	//编号
	@Override
	public String toString() {
		return "Fruits [kinds=" + kinds + ", num=" + num + ", size=" + size + ", date=" + date + "]";
	}
	private String size;	//大小
	
	//@JSONField(format="yyyy-MM-dd HH:mm:ss SSS") 
	@JSONField(format="yyyy-MM-dd")	//序列化时间
	private Date date;		//上市时间
	
	public Fruits() {
	}
	public Fruits(String kinds, Integer num, String size, Date date) {
		this.kinds = kinds;
		this.num = num;
		this.size = size;
		this.date = date;
	}
	public String getKinds() {
		return kinds;
	}
	public void setKinds(String kinds) {
		this.kinds = kinds;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
}
