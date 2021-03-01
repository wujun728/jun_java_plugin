package com.ky26.object;

public class Test {
	private String name;
	public void setName(String name){
		this.name=name;
	}
	public void getName(){
		System.out.println("设置的名字是"+name);
	}
	public static void main(String[] args) {
		Test test=new Test();
		test.setName("java");
		test.getName();
	}
}
