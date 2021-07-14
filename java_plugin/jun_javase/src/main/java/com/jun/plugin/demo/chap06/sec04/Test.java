package com.jun.plugin.demo.chap06.sec04;

public class Test {

	/**
	 * ���ͷ���
	 * @param t
	 */
	public static <T> void f(T t){
		System.out.println("T�������ǣ�"+t.getClass().getName());
	}
	
	public static void main(String[] args) {
		f("");
		f(1);
		f(1.0f);
		f(new Object());
	}
}
