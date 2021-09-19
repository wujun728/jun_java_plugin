package com.jun.plugin.demo.chap03.sec12;

public class Test extends C implements A,B {

	@Override
	public void a() {
		System.out.println("a����");
	}
	
	@Override
	public void b() {
		System.out.println("b����");
	}

	public static void main(String[] args) {
		Test t=new Test();
		t.a();
		t.b();
		t.c();
		System.out.println(Test.TITLEA);
		System.out.println(Test.TITLEB);
	}
}
