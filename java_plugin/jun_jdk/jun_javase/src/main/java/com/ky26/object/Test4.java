package com.ky26.object;

public class Test4 {
	public static void main(String[] args) {
		Animal a1=new Cat();//向上转型
		a1.eat();
		
		//a1.catchMouse();报错
		/*Cat c1=(Cat) a1;//向下转型
		c1.catchMouse();*/
		
		/*Dog d1=(Dog)a1;
		d1.lookHome();//同级，输入不报错，执行报错；*/
		
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
		System.out.println("吃鱼");
	}
	void catchMouse(){
		System.out.println("抓老鼠");
	}
}

class Dog extends Animal{
	void eat(){
		System.out.println("啃骨头");
	}
	void lookHome(){
		System.out.println("看家");
	}
}

class Pig extends Animal{
	void eat(){
		System.out.println("吃饲料");
	}
	void gongdi(){
		System.out.println("拱地");
	}
}

