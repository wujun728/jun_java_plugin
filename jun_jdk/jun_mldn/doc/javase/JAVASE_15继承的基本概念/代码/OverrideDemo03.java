class A{
	private void fun(){
		System.out.println("A类中定义的方法。") ;
	}
	public void print(){
		this.fun() ;		// 调用fun()方法
	}
};
class B extends A {
	void fun(){	// 此方法实际上属于新建的一个方法
		System.out.println("B类中定义的方法。") ;
	}
};
public class OverrideDemo03{
	public static void main(String args[]){
		B b = new B() ;
		b.print() ;
	}
};