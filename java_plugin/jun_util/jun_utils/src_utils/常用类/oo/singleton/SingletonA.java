package book.oo.singleton;

public class SingletonA {
	//私有属性
	private static int id = 1;
	//SingletonA的唯一实例
	private static SingletonA instance = new SingletonA();
	
	//将构造函数私有，防止外界构造SingletonA实例
	private SingletonA() {
	}
	/**
	 * 获取SingletonA的实例
	 */
	public static SingletonA getInstance() {
		return instance;
	}
	/**
	 * 获取实例的id,synchronized关键字表示该方法是线程同步的，
	 * 即任一时刻最多只能有一个线程进入该方法
	 * @return
	 */
	public synchronized int getId() {
		return id;
	}
	/**
	 * 将实例的id加1
	 */
	public synchronized void nextID() {
		id++;
	}

}