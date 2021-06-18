package com.java1234.chap03.sec09;

public class Cat extends Animal{

	private String address;
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Cat() {
		super();
		System.out.println("子类无参构造方法");
	}

	public Cat(String name, int age,String address) {
		super(name, age);
		this.address=address;
		System.out.println("子类有参构造方法");
	}

	/**
	 * 重写父类的say方法
	 */
	public void say(){
		// 调用父类的say方法
		super.say();
		System.out.println("我是一只猫，我叫："+this.getName()+"我的年龄是："+this.getAge()+",我来自"+this.getAddress());
	}
	
	public static void main(String[] args) {
		Cat cat=new Cat("Mini",2,"火星");
		cat.say();
	}
}
