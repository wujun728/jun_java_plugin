package com.java1234.chap03.sec14;

public class People {

	private String name;
	
	public People(String name) {
		this.name = name;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		return this.getName();
	}


	@Override
	public boolean equals(Object obj) {
		String name=((People)obj).getName();
		System.out.println(name);
		return this.name==name;
	}



	public static void main(String[] args) {
		People p1=new People("张三");
		People p2=new People("张三");
		People p3=new People("李四");
		System.out.println("p1.equals(p2):"+p1.equals(p2));
		System.out.println("p1.equals(p3):"+p1.equals(p3));
		System.out.println(p1);
		System.out.println(p1.toString());
	}
}
