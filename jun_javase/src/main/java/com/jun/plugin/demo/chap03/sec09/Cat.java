package com.jun.plugin.demo.chap03.sec09;

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
		System.out.println("�����޲ι��췽��");
	}

	public Cat(String name, int age,String address) {
		super(name, age);
		this.address=address;
		System.out.println("�����вι��췽��");
	}

	/**
	 * ��д�����say����
	 */
	public void say(){
		// ���ø����say����
		super.say();
		System.out.println("����һֻè���ҽУ�"+this.getName()+"�ҵ������ǣ�"+this.getAge()+",������"+this.getAddress());
	}
	
	public static void main(String[] args) {
		Cat cat=new Cat("Mini",2,"����");
		cat.say();
	}
}
