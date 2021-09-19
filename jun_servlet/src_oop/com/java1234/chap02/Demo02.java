package com.java1234.chap02;

public class Demo02 {

	/**
	 * main  alt+/ 自动提示
	 * @param args
	 */
	public static void main(String[] args) {
		// 定义一个float类型的变量 
		// 小数默认是double类型，所以我们必须加一个f，来表示float类型
		float f=0.1f;
		System.out.println("f="+f);
		
		// 定义一个double类型的变量
		double d=1.2;
		// 快捷方式 syso  alt+/
		System.out.println("d="+d);
		
		// 把float类型的最大值赋值给maxF变量
		float maxF=Float.MAX_VALUE;
		System.out.println("maxF="+maxF);
		
	}
}
