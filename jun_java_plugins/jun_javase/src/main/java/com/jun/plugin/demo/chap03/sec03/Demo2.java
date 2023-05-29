package com.jun.plugin.demo.chap03.sec03;

public class Demo2 {

	void fun1(){
		System.out.println("����һ����ͨ����");
	}
	
	static void fun2(){
		System.out.println("����һ����̬����");
	}
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Demo2 demo2=new Demo2();
		// ������ͨ����  ����.����
		demo2.fun1();
		
		// ���þ�̬����  ����.����
		Demo2.fun2();
		// ���þ�̬����  ����.����
		demo2.fun2();
	}
}
