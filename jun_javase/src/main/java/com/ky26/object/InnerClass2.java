package com.ky26.object;

public class InnerClass2 {
	public static void main(String[] args) {
		new Demo11(){
			void show(){
				System.out.println("重写抽象类方法");
			}
			void show1(){
				System.out.println("子类独有方法");
			}
		}.show1();
	}
}
abstract class Demo11{
	abstract void show();
}
class Outer1{
	void Method(){
		/*new Demo11(){
			void show(){
				System.out.println("重写抽象类方法");
			}
			void show1(){
				System.out.println("子类独有方法");
			}
		}.show1();//借用父类名字，建立子类实例，子类既可以访问父类方法，也可以访问子类方法；向上转型（多态）
		*/
		
		/*Demo11 d=new Demo11(){
			void show(){
				System.out.println("重写抽象类方法");
			}
			void show1(){
				System.out.println("子类独有方法");
			}
		};//创建一个抽象父类实例，调用子类特有方法show1会报错
		d.show();
		d.show1();*/
	}
}



