class A{
	public void fun(){
		System.out.println("A类中定义的方法。") ;
	}
};
class B extends A {
	public void fun(){	// 称为方法覆写
		System.out.println("B类中定义的方法。") ;
	}
};
public class OverrideDemo01{
	public static void main(String args[]){
		B b = new B() ;
		b.fun() ;
	}
};