package com.jun.plugin.javase.object;

public class Test7 {
	public static void main(String[] args) {
		Outer3 o1=new Outer3();
		o1.method();
	}
}
abstract class Demo22{
	abstract void show();
}
class Outer3{
	void method(){
		
		/*new Demo22(){
			void show(){
				System.out.println("122");
			}
			void show1(){
				System.out.println("222");
			}
		}.show();�������*/
		
		/*Demo22 d1=new Demo22(){
			void show(){
				System.out.println("22");
			}
			void show1(){
				System.out.println("11");
			}
		};
		d1.show();�������*/
	}
}
