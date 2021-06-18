package com.ky26.object;

public class SuperKey {
	public static void main(String[] args) {
		ChildClass cc=new ChildClass();
		cc.f();
	}
}
class FatherClass{
	public int value;
	public void f(){
		value=10;
		System.out.println("父类值"+this.value);
	}
}
class ChildClass extends FatherClass{
	public int value;//除了继承父类的value，还自己另外定义了一个value；
	public void f(){
		super.f();//使子类对象中的父类对象自己调用自己的f方法，改变自身（父类）的值;
		value=200;
		System.out.println("子类值"+this.value);
		System.out.println(value);
		System.out.println(super.value);//从父类继承来的value的值；
	}
}

/*
super指向父类，是父类的引用
this指向子类，是子类的引用,只能用在非静态方法体中
当new出来一个对象时，这个对象会产生一个this的引用，指向对象本身，
如果new出来的对象时一个子类对象的话，那么这个子类对象里面还会有一个super引用，指向当前对象的父对象
*/









