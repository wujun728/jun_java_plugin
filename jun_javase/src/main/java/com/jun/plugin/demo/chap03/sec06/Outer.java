package com.jun.plugin.demo.chap03.sec06;

public class Outer {

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
	
	public void show(){
		Inner inner=new Inner();
		inner.show();
	}
	
	public static void main(String[] args) {
		Outer outer=new Outer();
		outer.show();
	}
}
