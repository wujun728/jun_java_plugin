package com.jun.plugin.javase.object;

public class Test111 {
	public void test(Bird bird){
		System.out.println(bird.getName()+"�ܹ���"+bird.fly()+"��");
	}
	
	public static void main(String[] args) {
		Test111 test=new Test111();
		test.test(new Bird(){
			public int fly(){
				return 1000;
			}
			public String getName(){
				return "����";
			}
		});
	}
}


abstract class Bird {
	private String name;
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name=name;
	}
	public abstract int fly();
}
