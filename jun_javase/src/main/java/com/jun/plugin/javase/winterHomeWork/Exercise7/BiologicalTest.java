package com.jun.plugin.javase.winterHomeWork.Exercise7;

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
		System.out.println("����˯��");
	}

	public void eat() {
		System.out.println("���ܳԷ�");
	}

	public void breathe() {
		System.out.println("���ܺ���");
	}

	public void thinking() {
		System.out.println("����˼��");
	}

	public void study() {
		System.out.println("����ѧϰ");
	}
	
}
