package com.java1234.chap03.sec03;

/**
 * 三围类
 * @author caofeng
 *
 */
class SanWei{
	int b; // 胸围
	int w; // 腰围
	int h; // 臀围
}

public class People5 {

	/**
	 * 报三围
	 * @param age 年龄
	 * @param sanWei 
	 */
	void speak(int age,SanWei sanWei){
		System.out.println("我今年"+age+"岁了,我的三围是："+sanWei.b+","+sanWei.w+","+sanWei.h);
		age=24;
		sanWei.b=80;
	}
	
	public static void main(String[] args) {
		People5 xiaoli=new People5();
		int age=23;
		SanWei sanWei=new SanWei();
		sanWei.b=90;
		sanWei.w=60;
		sanWei.h=90;
		// age传递的是值，sanWei传递的是引用(地址),c里叫指针
		xiaoli.speak(age,sanWei);
		System.out.println(age);
		System.out.println(sanWei.b);
	}
}
