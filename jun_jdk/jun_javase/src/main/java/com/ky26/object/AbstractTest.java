package com.ky26.object;

public class AbstractTest {
	public static void main(String[] args) {
		/*Test4 t1=new Test4();
		t1.show();
		Test5 t2=new Test5();
		t2.show();*/
		
		
		/*Test4 t3=new Test4();
		Test5 t5=(Test5)t3;//书写时不报错，执行时报错
		System.out.println(t5);
		System.out.println(t3 instanceof Test5);*/
		
/*		
		Test4 t3=new Test5();//向上转型；父类的引用=子类的实例
		Test5 t5=(Test5)t3;//向下转型
		//Test5 t6=new Test4();直接在子类中建立父类对象的引用会报错；向下转型之后的对象才能进行向上转型？？？
		
		//t3.print();执行报错，向上转型后父类的引用指向子类的实例对象，父类的引用不能直接调用子类特有的功能
		System.out.println(t3);
		System.out.println(t5);//转型前后的对象是同一个，只是所属的类发生了变化
		System.out.println(t3 instanceof Test5);*/
		
//		Test5 t4=new Test5();
//		System.out.println(t4 instanceof Test4);
//		System.out.println(t3 instanceof Test5);//子类继承父类，因此子类的实例化对象也是父类的实例化对象
		
	}
	
	
}
/*class Test4{
	static void show(){
		System.out.println(1);
	}
}

class Test5 extends Test4{
	void print(){
		System.out.println("子类特有的方法");
	}
}
final class Test6{
	private int a=5;
	void show(){
		System.out.println(a);
	}
}*/
