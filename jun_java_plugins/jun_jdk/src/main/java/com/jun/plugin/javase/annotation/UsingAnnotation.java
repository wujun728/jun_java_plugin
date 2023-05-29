package com.jun.plugin.javase.annotation;

import java.lang.reflect.Method;

/**
 * 一个实例程序应用前面定义的Annotation：AnnotationDefineForTestFunction
 * 
 * @author Wujun
 * 
 */
public class UsingAnnotation {
	@AnnotationDefineForTestFunction
	public static void method01() {
	}

	public static void method02() {
	}

	@AnnotationDefineForTestFunction
	public static void method03() {
		throw new RuntimeException("method03");
	}

	public static void method04() {
		throw new RuntimeException("method04");
	}

	public static void main(String[] argv) throws Exception {
		int passed = 0, failed = 0;
		// 被检测的类名
		String className = "com.ketayao.learn.javase.annotation.UsingAnnotation";
		// 逐个检查此类的方法，当其方法使用annotation声明时调用此方法
		for (Method m : Class.forName(className).getMethods()) {
			if (m.isAnnotationPresent(AnnotationDefineForTestFunction.class)) {
				try {
					m.invoke(null);
					passed++;
				} catch (Throwable ex) {
					System.out.printf("测试 %s 失败: %s %n", m, ex.getCause());
					failed++;
				}
			}
		}
		System.out.printf("测试结果：通过: %d, 失败： %d%n", passed, failed);
	}
}