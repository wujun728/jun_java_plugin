package com.jun.plugin.demo.chap03.sec12;

public class Test2 extends C implements D {

	@Override
	public void a() {
		System.out.println("a����");
	}
	
	@Override
	public void b() {
		System.out.println("b����");
	}

	@Override
	public void d() {
		System.out.println("d����");
	}
	
	public static void main(String[] args) {
		Test2 t=new Test2();
		t.a();
		t.b();
		t.c();
		t.d();
		System.out.println(Test2.TITLEA);
		System.out.println(Test2.TITLEB);
	}

}
