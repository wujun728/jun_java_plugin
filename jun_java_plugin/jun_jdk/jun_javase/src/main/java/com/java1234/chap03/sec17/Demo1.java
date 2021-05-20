package com.java1234.chap03.sec17;

public class Demo1 {

	public static void main(String[] args) {
		int a=1;
		Integer i=new Integer(a);  // 装箱 把基本变量变成对象变量
		int b=i.intValue(); // 拆箱  把对象变量变成基本变量
		System.out.println("a="+a+",i="+i+",b="+b);
	}
}
