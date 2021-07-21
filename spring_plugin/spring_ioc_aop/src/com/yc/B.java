package com.yc;

public class B {
	A a;
	
	public B(){
		
	}
	public B(A a){
		this.a=a;
	}
	public A getA() {
		return a;
	}
	public void setA(A a) {
		this.a = a;
	}
	public void b(){
		System.out.println("BµÄ·½·¨b");
	}
}
