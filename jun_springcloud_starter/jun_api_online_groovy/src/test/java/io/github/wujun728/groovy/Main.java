package io.github.wujun728.groovy;

public class Main {
	
	public int age = 666;

	public void sayHello() {
		System.out.println("年龄是:" + age);
	}
	public void sayHello(Param p) {
		System.out.println("年龄是:" + p.getMsg());
	}
}
