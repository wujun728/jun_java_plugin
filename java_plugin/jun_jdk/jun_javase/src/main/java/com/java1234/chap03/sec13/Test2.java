package com.java1234.chap03.sec13;

public class Test2 {

	public static void main(String[] args) {
		
		// 父类引用指向Dog类的具体实现  向上转型
		Animal2 animal2=new Dog2();
		animal2.say2();
		
		animal2=new Cat2();
		animal2.say2();
		
		
	}
}
