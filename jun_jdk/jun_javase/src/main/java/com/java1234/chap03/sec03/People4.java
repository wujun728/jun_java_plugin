package com.java1234.chap03.sec03;

public class People4 {

	// 返回类型
	int speak(String name,int age,String ...hobbies){
		System.out.println("我叫"+name+",我今年"+age+"岁了");
		System.out.print("我的爱好：");
		for(String hobby:hobbies){
			System.out.print(hobby+" ");
		}
		// 获取爱好的长度
		int totalHobbies=hobbies.length;
		return totalHobbies;
	}
	
	public static void main(String[] args) {
		People4 zhangsan=new People4();
		int n=zhangsan.speak("张三",23,"游泳","唱歌","跳舞");
		System.out.println("\n有"+n+"个还好");
	}
}
