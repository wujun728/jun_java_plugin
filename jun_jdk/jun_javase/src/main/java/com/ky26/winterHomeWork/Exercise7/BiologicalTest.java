package com.ky26.winterHomeWork.Exercise7;

public class BiologicalTest {
	
}
interface Biological{
	public abstract void breathe();
}
interface Animal extends Biological{
	public abstract void sleep();
	public abstract void eat();
}
interface People extends Animal{
	public abstract void thinking();
	public abstract void study();
}

class Person implements People{

	public void sleep() {
		System.out.println("人能睡觉");
	}

	public void eat() {
		System.out.println("人能吃饭");
	}

	public void breathe() {
		System.out.println("人能呼吸");
	}

	public void thinking() {
		System.out.println("人能思考");
	}

	public void study() {
		System.out.println("人能学习");
	}
	
}
