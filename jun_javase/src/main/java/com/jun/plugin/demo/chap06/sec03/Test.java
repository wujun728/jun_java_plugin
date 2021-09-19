package com.jun.plugin.demo.chap06.sec03;

import com.jun.plugin.demo.chap06.sec02.Animal;
import com.jun.plugin.demo.chap06.sec02.Cat;
import com.jun.plugin.demo.chap06.sec02.Demo;
import com.jun.plugin.demo.chap06.sec02.Dog;

public class Test {
	
	/**
	 * ͨ�������
	 * @param a
	 */
	private static void take(Demo<?> a){
		a.print();
	}
	
	public static void main(String[] args) {
		Demo<Dog> dog=new Demo<Dog>(new Dog());
		take(dog);
		Demo<Cat> cat=new Demo<Cat>(new Cat());
		take(cat);
		Demo<Animal> animal=new Demo<Animal>(new Animal());
		take(animal);
	}
}
