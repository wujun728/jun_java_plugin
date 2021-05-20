interface A{
	public void fun() ;
	abstract class B{	// 内部抽象类
		public abstract void print() ;
	}
};
class X implements A{
	public void fun(){
		System.out.println("****************") ;
	}
	class Y extends B{
		public void print(){
			System.out.println("===================") ;
		}
	};
};
public class TestDemo02{
	public static void main(String args[]){
		A a = new X() ;
		a.fun() ;
		A.B b = new X().new Y() ;
		b.print() ;
	}
};