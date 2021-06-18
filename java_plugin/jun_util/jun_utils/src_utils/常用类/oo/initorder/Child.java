package book.oo.initorder;

public class Child extends Parent {
	{
		System.out.println("Child的初始化块");
		int bx = 100;
	}
	static {
		System.out.println("Child的静态初始化块");
	}
	public Child() {
		super();
		System.out.println("Child的构造方法被调用");
	}
	public static void dispB(){
		System.out.println("Child的dispB()被调用");
	}
	/**
	 * 当Java在进行垃圾回收时，会调用对象的finalize方法
	 */
	protected void finalize() {
		System.out.println("Child的销毁方法被调用");
		super.finalize();
	}
}