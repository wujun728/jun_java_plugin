package book.oo.singleton;

/*
 * 模式名称：单建模式
 * 模式特征：只能创建该类的一个实例
 * 模式用途：提供一个全局共享类实例
 **/
public class SingletonTest {

	public static void main(String[] args) {

		SingletonA a1 = SingletonA.getInstance();
		SingletonA a2 = SingletonA.getInstance();
		System.out.println("用SingletonA实现单例模式");
		System.out.println("调用nextID方法前：");
		System.out.println("a1.id=" + a1.getId());
		System.out.println("a2.id=" + a2.getId());
		a1.nextID();
		System.out.println("调用nextID方法后：");
		System.out.println("a1.id=" + a1.getId());
		System.out.println("a2.id=" + a2.getId());
		//SingletonA和SingletonB的区别：前者是在类被加载的时候就创建了实例，
		//而后者是在调用getInstance方法时才创建实例。
		SingletonB b1 = SingletonB.getInstance();
		SingletonB b2 = SingletonB.getInstance();
		System.out.println("用SingletonB实现单例模式");
		System.out.println("调用nextID方法前：");
		System.out.println("b1.id=" + b1.getId());
		System.out.println("b2.id=" + b2.getId());
		b1.nextID();
		System.out.println("调用nextID方法后：");
		System.out.println("b1.id=" + b1.getId());
		System.out.println("b2.id=" + b2.getId());
	}
}