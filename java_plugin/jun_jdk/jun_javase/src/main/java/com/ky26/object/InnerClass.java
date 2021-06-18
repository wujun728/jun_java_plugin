package com.ky26.object;

public class InnerClass {
	public static void main(String[] args) {
		/*Outer.Inner o=new Outer().new Inner();//成员内部类——内部类不被static修饰则使用此方法声明
		o.show();
		Outer o1=new Outer();
		o1.showInner();//访问内部类的变量*/
		
		
		/*Outer.Inner in=new Outer.Inner();//成员内部类——若内部类被static修饰则使用此方法声明
		in.show();*/
		
		//Object o=new Outer().method();//局部内部类
		//System.out.println(o);//局部内部类中的方法和变量作用域仅限于该方法体或改内部类中
		Outer.Inner o1=new Outer().new Inner();
	}
}
class Outer{
	int number=5;
	class Inner{
		int number=6;
		void show(){
			int number=7;
			System.out.println("-----------"+number);
		}
	}
	/*
	void showInner(){
		Inner a1=new Inner();
		a1.show();//6
		System.out.println(a1.number);//7
	}*/
	
	static{
		System.out.println("我是外部类的静态块");
	}
	Outer(){
		System.out.println("我是外部类的构造函数");
	}
	
	Object method(){
		class Inner{
			int a=12;
			/*static{
				System.out.println("我是内部类的静态块");
			}*/
			Inner(){
				System.out.println("我是内部类的构造函数"+"-------a="+a);
			}
			void show(){
				System.out.println("外部类中的变量number是："+number);
				System.out.println("局部内部类的方法");
			}
		}
		Inner in=new Inner();
		in.show();
		return in;
	}
}
//内部类的加载顺序问题:



