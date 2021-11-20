package com.jun.plugin.demo.chap03.sec13;

public class Test {

	public static void main(String[] args) {
		/*Dog dog=new Dog();
		dog.say();
		
		Cat cat=new Cat();
		cat.say();*/
		
		// ��������ָ��Dog��ľ���ʵ��
		Animal animal=new Dog();
		animal.say();
		
		// ����ת��
		Dog dog=(Dog) animal;
		dog.say();
		
		// ����ת�Ͷ�  ����ȫ
		Cat cat=(Cat)animal;
		cat.say();
		
		/*animal=new Cat();
		animal.say();*/
	}
}
