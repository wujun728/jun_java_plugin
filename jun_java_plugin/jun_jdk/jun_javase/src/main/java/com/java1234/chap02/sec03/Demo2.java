package com.java1234.chap02.sec03;

public class Demo2 {

	public static void main(String args[]){
		// 定义一个float类型变量f
		// 小数默认是double类型,所以我们必须加一个f,来表示float类型
		float f=1.1f;
		System.out.println("f="+f);
		
		// 定义一个double类型变量d
		double d=1.2;
		// 快捷方式   syso   Alt+/
		System.out.println("d="+d);
		
		// 获取float的最大值
		// ctrl 鼠标点击
		float maxF=Float.MAX_VALUE;
		System.out.println("float的最大值："+maxF);
		
	}
}
