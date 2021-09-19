package com.jun.plugin.demo.chap03.sec03;

public class Demo {

	int add(int a,int b){
		System.out.print("����һ��");
		return a+b;
	}
	
	/**
	 * ���������أ�����������һ��
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	int add(int a,int b,int c){
		System.out.print("��������");
		return a+b+c;
	}
	
	/**
	 * ���������أ����������Ͳ�һ��
	 * @param a
	 * @param b
	 * @return
	 */
	int add(int a,String b){
		System.out.print("��������");
		return a+Integer.parseInt(b);
	}
	
	
	
	public static void main(String[] args) {
		Demo demo=new Demo();
		System.out.println(demo.add(1, 2));
		System.out.println(demo.add(1, 2,3));
		System.out.println(demo.add(1, "3"));
		
	}
}
