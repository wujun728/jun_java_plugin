package net.jueb.util4j.study.jdk8.function;

import java.util.function.Function;

public class Test {
	/**
	 * String输入,Integer输出
	 */
	public static Function<String, Integer> f = s -> s.length();
	
	public static Runner r = ()->{System.out.println("空函数");};
	
	/**
	 * 子接口可继承
	 */
	public static Runner2 r2 = ()->{System.out.println("空函数");};
	
	public static Function<Object,Object> function = s -> {
		return null;
	};
	
	public static void main(String[] args) {
		int len=Test.f.apply("helloWorld");
		System.out.println(len);
		Test.r.run();
	}
}
