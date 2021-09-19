package com.java1234.chap03.sec12;

public class Test2 extends C implements D{

	@Override
	public void a() {
		System.out.println("a方法");
	}
	
	@Override
	public void b() {
		System.out.println("b方法");
	}
	
	@Override
	public void d() {
		System.out.println("d方法");
	}

	public static void main(String[] args) {
		Test2 t=new Test2();
		t.a();
		t.b();
		t.c();
		t.d();
		System.out.println(Test2.TITLE);
		System.out.println(Test2.TITLE2);
	}


	
}
