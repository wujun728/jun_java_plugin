package com.ky26.object;

public class Test5 {
	public static void main(String[] args) {
		/*Demo3 d1=new Demo33();
		d1.show();*/
		/*Demo3 d2=new Demo3();
		d2.show();*/
		Outer21 d1=new Outer21();
		System.out.println(d1.method());
		
		
	}
}

interface Demo3{
	abstract void print();
}

class Outer21{
	public Demo3 method(){
		return new Demo3(){
			public void print(){
				System.out.println("1");
			}
		};
	}
}