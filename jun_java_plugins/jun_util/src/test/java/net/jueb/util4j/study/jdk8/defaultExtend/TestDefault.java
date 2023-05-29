package net.jueb.util4j.study.jdk8.defaultExtend;

public class TestDefault {

	public interface A{
		default void hello(){
			System.out.println("hello A");
		}
	}
	
	public interface B extends A{
		default void hello(){
			System.out.println("hello B");
		}
	}
	
	public static class C implements B,A{
		
	}
	
	public static class D implements B,A{
		@Override
		public void hello() {
			B.super.hello();
		}
	}
	
	public static void main(String[] args) {
		new C().hello();//hello B
		new D().hello();//hello B
	}
}
