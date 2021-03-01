class A{
	public void fun(){
		System.out.println("A类中定义的方法。") ;
	}
};
class B extends A {
	public void fun(){	// 此方法实际上属于新建的一个方法
		super.fun() ;	// 调用父类中的fun()方法
		System.out.println("B类中定义的方法。") ;
	}
};
public class OverrideDemo04{
	public static void main(String args[]){
		B b = new B() ;
		b.fun() ;
	}
};