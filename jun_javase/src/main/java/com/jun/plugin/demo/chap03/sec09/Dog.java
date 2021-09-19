package com.jun.plugin.demo.chap03.sec09;

/**
 * ����Dog�࣬�̳���Animal��
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
