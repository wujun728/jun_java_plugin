package com.java1234.chap03.sec03;

public class Person5 {

	/**
	 * 
	 * @param name
	 * @param age
	 * @param hobbies
	 * @return 返回爱好的个数
	 */
	int speak(String name,int age,String ...hobbies){
		System.out.println("我是"+name+",我今年"+age+"岁了");
		for(String hobby:hobbies){
			System.out.print(hobby+" ");
		}
		int totalHobbies=hobbies.length;
		return totalHobbies;
	}
	
	public static void main(String[] args) {
		Person5 zhangsan=new Person5();
		int n=zhangsan.speak("张三",23,"游泳","唱歌","跳舞");
		System.out.println("有"+n+"个爱好");
	}
}
