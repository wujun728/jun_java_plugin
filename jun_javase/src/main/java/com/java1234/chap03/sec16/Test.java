package com.java1234.chap03.sec16;

public class Test {

	public void test(A a){
		a.a();
	}
	
	
	public static void main(String[] args) {
		Test t=new Test();
		t.test(new B());
		
		
		// 匿名内部类
		t.test(new A(){

			@Override
			public void a() {
				// TODO Auto-generated method stub
				System.out.println("匿名内部类，一次性使用！");
			}
			
		});
	}
}
