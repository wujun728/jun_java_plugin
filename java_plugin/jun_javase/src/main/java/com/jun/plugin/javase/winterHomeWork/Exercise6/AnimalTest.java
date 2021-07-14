package com.jun.plugin.javase.winterHomeWork.Exercise6;

public class AnimalTest {
	public static void main(String[] args) {
		Rabbit r1=new Rabbit();
		r1.eat();
		r1.sleep();
		Tiger t1=new Tiger();
		t1.eat();
		t1.sleep();
	}
}
class Animal{
	public void eat(){
		
	}
	public void sleep() {
		System.out.println("˯����");
	}
}
class Rabbit extends Animal{
	public void eat(){
		System.out.println("�ҳԲ�");
	}
}
class Tiger extends Animal{
	public void eat(){
		System.out.println("�ҳ���");
	}
}
