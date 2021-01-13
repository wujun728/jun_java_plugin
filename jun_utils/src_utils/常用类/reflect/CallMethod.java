package book.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 通过反射动态调用类的静态方法和实例方法。
 */
public class CallMethod {

	public static void main(String[] args) throws Exception {
		// 获取TestClass的Class对象
		Class testClass = Class.forName(TestClass.class.getName());

		/** *** 创建实例 **** */
		// （1）使用Class对象的newInstance方法创建一个实例，这种方法用默认构造方法创建对象
		TestClass objectA = (TestClass) testClass.newInstance();
		System.out.println("Class的newInstance() 方法创建默认TestClass实例: "
				+ objectA.toString());
		// （2）使用构造方法创建实例。这就可以使用带参数的构造方法创建实例了
		Constructor[] cons = testClass.getDeclaredConstructors();
		System.out.println("testClass有 " + cons.length + " 个构造方法");
		Constructor con = null;
		for (int i = 0; i < cons.length; i++) {
			con = cons[i];
			// 默认构造函数
			if (con.getParameterTypes().length == 0) {
				// 调用Constructor的newInstance方法创建实例
				objectA = (TestClass) con.newInstance(null);
				System.out
						.println("Constructor 的 newInstance() 方法创建默认TestClass实例: "
								+ objectA.toString());
			} else {
				// 带参数的构造函数
				objectA = (TestClass) con.newInstance(new Object[] {
						new Integer(55), new Integer(88) });
				System.out
						.println("Constructor 的 newInstance() 方法创建带参数的TestClass实例: "
								+ objectA.toString());
			}
		}

		/** *** 获取方法 *** */
		// 获取所有方法
		Method[] methods = testClass.getMethods();
		// 获取某个特定的无参数的方法
		Method saddMethod1 = testClass.getMethod("sadd", null);
		Method addMethod1 = testClass.getMethod("add", null);
		// 获取某个特定的有参数的方法
		Method saddMethod2 = testClass.getMethod("sadd", new Class[] {
				int.class, int.class });
		Method addMethod2 = testClass.getMethod("add", new Class[] { int.class,
				int.class });

		/** *** 调用静态方法 **** */
		// 调用不带参数的静态方法
		int result = ((Integer) saddMethod1.invoke(null, null)).intValue();
		System.out.println("调用不带参数的静态方法sadd: " + result);
		// 调用带参数的静态方法
		result = ((Integer) saddMethod2.invoke(null, new Object[] {
				new Integer(30), new Integer(70) })).intValue();
		System.out.println("调用带参数30, 70的静态方法sadd: " + result);

		/** *** 调用实例方法 **** */
		objectA = (TestClass) testClass.newInstance();
		// 调用不带参数的实例方法
		result = ((Integer) addMethod1.invoke(objectA, null)).intValue();
		System.out.println("调用不带参数的实例方法add: " + result);
		// 调用带参数的实例方法
		result = ((Integer) addMethod2.invoke(objectA, new Object[] {
				new Integer(130), new Integer(170) })).intValue();
		System.out.println("调用带参数130, 170的实例方法add: " + result);

		// 不能访问私有方法
//		Method sub = testClass.getMethod("sub", null);
//		System.out.println(sub.invoke(objectA, null));
	}

	// 测试类
	static class TestClass {
		// 两个静态属性
		static int sa = 100;
		static int sb = 50;
		// 两个实例属性
		int a;
		int b;
		// 默认构造方法
		public TestClass() {
			this.a = 5;
			this.b = 10;
		}
		// 带参数的构造方法
		public TestClass(int a, int b) {
			this.a = a;
			this.b = b;
		}

		// 静态方法，实现add功能
		public static int sadd() {
			return sa + sb;
		}
		public static int sadd(int a, int b) {
			return a + b;
		}
		// 实例方法，实现add功能
		public int add() {
			return this.a + this.b;
		}
		public int add(int a, int b) {
			return a + b;
		}
		public String toString() {
			return "a = " + this.a + "; b = " + this.b;
		}
		// 私有方法
		private int sub() {
			return this.a - this.b;
		}
	}
}
