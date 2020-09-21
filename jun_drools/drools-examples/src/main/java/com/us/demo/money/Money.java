package com.us.demo.money;

import org.drools.core.WorkingMemory;

public class Money {
	private int number;
	private String desc;
	public Money() {
		super();
	}
	
	public Money(int number) {
		//super();
		//this();
		this.number=number;
	}
	
	public Money(int number,String desc) {
		//super();
		//this.number=number;
		// 此处再加super 因为相当于引用了两次super 这个是不会允许的
		// 会默认添加 super 执行父类的构造函数
		//this(number);
		this.number=number;
		this.desc=desc;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String toString(){
		return ("[Number:"+number+" ]  [Desc:"+desc+"]");
	}
//	WorkingMemory

}
