package com.java1234.chap06.sec01;

public class C12 {

	private Object object;
	
	

	public C12(Object object) {
		super();
		this.object = object;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}
	
	public void print(){
		System.out.println("object的类型是："+object.getClass().getName());
	}
	
}
