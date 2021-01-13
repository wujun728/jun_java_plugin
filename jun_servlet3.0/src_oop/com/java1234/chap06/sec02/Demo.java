package com.java1234.chap06.sec02;

public class Demo<T extends Animal> {

	private T t;
	
	
	public Demo(T t) {
		super();
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public void setT(T t) {
		this.t = t;
	}
	
	public void print(){
		System.out.println("t的类型是："+t.getClass().getName());
	}
}
