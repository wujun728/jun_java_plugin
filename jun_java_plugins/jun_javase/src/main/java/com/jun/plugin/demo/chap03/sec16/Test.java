package com.jun.plugin.demo.chap03.sec16;

public class Test {

	public void test(A a){
		a.a();
	}
	
	
	public static void main(String[] args) {
		Test t=new Test();
		t.test(new B());
		
		
		// �����ڲ���
		t.test(new A(){

			@Override
			public void a() {
				// TODO Auto-generated method stub
				System.out.println("�����ڲ��࣬һ����ʹ�ã�");
			}
			
		});
	}
}
