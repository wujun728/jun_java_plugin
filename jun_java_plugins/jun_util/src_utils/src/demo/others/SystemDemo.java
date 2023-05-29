package demo.others;
public class SystemDemo {
	public static void main(String[] args) {
		String[] s = new String[] { "liu" };
		String[] s2 = new String[] { "hai" };
		String[][] a = { s, s2, s, s, s, s2, s2, s };
		String[][] b = new String[10][10];
		/*
		 * System类包含一些有用的类字段和方法，是final类,无法被继承,构造方法是private，不能创建System对象。
		 * 所有public属性和方法都是final static
		 */
		// 1.数组复制，采用本地方法复制，实现了深拷贝
		System.arraycopy(a, 1, b, 0, 5);
		System.out.println(b[0][0]);
		// 2.已经过去的毫米数，从1970-1-1开始
		System.currentTimeMillis();
		// 3.提示虚拟机进行垃圾回收，通过调用Runtime.getRuntime().gc();实现
		System.gc();
		// 4.返回系统环境
		System.getenv();
		// 5.返回当前的系统属性。
		System.getProperties();
		// 6.可用於計數已過去的時間
		System.nanoTime();
		// 7.0表示正常退出，調用Runtime.getRuntime().exit(0);
		System.exit(0);
		// 8.返回给定对象的哈希码，该代码与默认的方法 hashCode() 返回的代码一样，无论给定对象的类是否重写 hashCode()。
		System.identityHashCode(null);
	}
}
