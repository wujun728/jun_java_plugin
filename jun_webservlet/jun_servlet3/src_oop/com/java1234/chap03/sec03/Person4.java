package com.java1234.chap03.sec03;

public class Person4 {

	/**
	 * 
	 * @param name 姓名参数
	 * @param age 年龄参数
	 * @param hobbies 爱好 个人不固定
	 */
	void speak(String name,int age,String ...hobbies){
		System.out.println("我是"+name+",我今年"+age+"岁了");
		for(String hobby:hobbies){
			System.out.print(hobby+" ");
		}
	}
	
	public static void main(String[] args) {
		Person4 zhangsan=new Person4();
		zhangsan.speak("张三",23,"游泳","唱歌","跳舞");
	}
}
