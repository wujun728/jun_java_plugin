package com.java1234.chap03.sec09;

/**
 * 定义Dog类，继承自Animal类
 * @author caofeng
 *
 */
public class Dog extends Animal{

	public static void main(String[] args) {
		Dog dog=new Dog();
		dog.setName("Pick");
		dog.setAge(1);
		dog.say();
	}
}
