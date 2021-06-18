abstract class A{
	public abstract void fun() ;
	interface B{	// 内部接口 
		public void print() ;
	}
};
class X extends A{
	public void fun(){
		System.out.println("****************") ;
	}
	class Y implements B{
		public void print(){
			System.out.println("===================") ;
		}
	};
};
public class TestDemo01{
	public static void main(String args[]){
		A a = new X() ;
		a.fun() ;
		A.B b = new X().new Y() ;
		b.print() ;
	}
};