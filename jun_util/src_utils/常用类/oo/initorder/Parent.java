package book.oo.initorder;

public class Parent {
	private int ix = 50;

	private static int iz = getNext(30);
	{
		System.out.println("Parent的初始化块");
		int x = 100;
		int y = getNext(100);
	}
	static {
		System.out.println("Parent的静态初始化块");
		int sx = 100;
		int sy = getNext(100);
	}
	public Parent() {
		System.out.println("Parent的构造方法被调用");
	}
	public void display() {
		System.out.println("Parent的display方法被调用");
		System.out.print("ix=" + this.ix);
		System.out.println("; iz=" + iz);
		dispA();
	}
	public static void dispA() {
		System.out.println("Parent的dispA()被调用");
	}
	public static int getNext(int base) {
		System.out.println("Parent的getNext(int base)被调用");
		return ++base;
	}
	/**
	 * 当Java在进行垃圾回收时，会调用对象的finalize方法
	 */
	protected void finalize() {
		System.out.println("Parent的销毁方法被调用");
	}
}