package com.java1234.chap06.sec01;

public class C12 {

	private Object object;

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public C12(Object object) {
		super();
		this.object = object;
	}
	
	/**
	 * 打印object的类型
	 */
	public void print(){
		System.out.println("object的类型是："+object.getClass().getName());
	}
}
