interface A{
	public static final String INFO = "CHINA" ;	// 全局常量
	public abstract void print() ;	// 抽象方法
	public abstract void fun() ;	// 抽象方法
}
class B implements A{	// 子类B实现了接口A
	public void print(){	// 实现抽象方法
		System.out.println("HELLO WORLD!!!") ;
	}
	public void fun(){
		System.out.println(INFO);	// 输出全局常量
	}
};
public class InterfaceDemo01{
	public static void main(String args[]){
		B b = new B() ;
		b.print() ;
		b.fun() ;
	}
};