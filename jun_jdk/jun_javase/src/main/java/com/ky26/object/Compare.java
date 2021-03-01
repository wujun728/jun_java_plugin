package com.ky26.object;

public class Compare {
	public static void main(String[] args) {
		Com num=new Com("张三",21);
		Com num2=new Com("张三",21);
		//System.out.println(num);
		//System.out.println(num2);
		System.out.println(num.compare(num2));
	}
}


class Com{
	String name;
	int age;
	Com(){

	}
	Com(String name,int age){
		this.name=name;
		this.age=age;
	}
	public void printThis() {
		System.out.println(this.name);
	}
	public boolean compare(Com obj){
		//System.out.println(obj);
		return (obj.age==this.age&&obj.name.equals(this.name));//同一个类的前提下可以使用此方法，因此还要判断是否在同一类中
	}
}


