package com.jun.plugin.javase.object;

public class Test4 {
	public static void main(String[] args) {
		Animal a1=new Cat();//����ת��
		a1.eat();
		
		//a1.catchMouse();����
		/*Cat c1=(Cat) a1;//����ת��
		c1.catchMouse();*/
		
		/*Dog d1=(Dog)a1;
		d1.lookHome();//ͬ�������벻����ִ�б���*/
		
		if(a1 instanceof Dog){
			System.out.println(1);
		}
		
		
	}
}
abstract class Animal{
	abstract  void eat();
}

class Cat extends Animal{
	void eat(){
		System.out.println("����");
	}
	void catchMouse(){
		System.out.println("ץ����");
	}
}

class Dog extends Animal{
	void eat(){
		System.out.println("�й�ͷ");
	}
	void lookHome(){
		System.out.println("����");
	}
}

class Pig extends Animal{
	void eat(){
		System.out.println("������");
	}
	void gongdi(){
		System.out.println("����");
	}
}

