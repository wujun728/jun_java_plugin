package com.jun.plugin.demo.chap03.sec06;

public class Outer2 {

	private int a=1;
	
	/**
	 * �����ڲ���
	 * @author caofeng
	 *
	 */
	class Inner{
		public void show(){
			System.out.println(a);
		}
	}
	
	public static void main(String[] args) {
		Outer2 outer2=new Outer2();  // ʵ�����ⲿ�����
		Outer2.Inner inner=outer2.new Inner();  // ʵ�����ڲ������
		inner.show();
	}
}
