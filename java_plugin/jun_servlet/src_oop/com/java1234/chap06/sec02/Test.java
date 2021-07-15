package com.java1234.chap06.sec02;

public class Test {

	public static void main(String[] args) {
		Demo<Dog> demo=new Demo<Dog>(new Dog());
		Dog dog=demo.getT();
		dog.print();
		
		Demo<Cat> demo2=new Demo<Cat>(new Cat());
		Cat cat=demo2.getT();
		cat.print();
		
		Demo<Animal> demo3=new Demo<Animal>(new Animal());
		
		// Demo<Integer> demo4=new Demo<Integer>(1);
	}
}
