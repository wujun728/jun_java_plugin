package demo.others;

public class ObjectDemo {
	/*
	 * 1.Object没有public 的静态属性和方法 
	 * 2.public final native Class<?> getClass()返回运行时类信息，
     * 3.toString 返回类名+@+哈希码值
	 * 4.其中wait和notify方法是final的，不可继承
	 * 5.equals方法只比较对象的引用,hashCode方法返回哈希码值。
	 * 6.重写equals方法要重写hashCode，因为相同的对象（通过equals比较返回true）
	 *   必须返回相同的哈希码。
	 * 7.finalize方法是一个protected空方法
	 * 8.protected native Object clone()返回一个副本，对象必须实现Cloneable接口
	 */
}
