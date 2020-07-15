package com.jun.plugin.arithmetic.sort;

import org.junit.Test;

public class Recursion {
	// 0,1,1,2,3,5,8
	public static void main(String[] args) throws Exception {
		System.out.println(factorial(10));
		// recursion_display(5);
		// System.out.println(fib(0)+"\t"+fib(1)+"\t"+fib(2)+"\t"+fib(3)+"\t"+fib(4)+"\t"+fib(5));
	}

	public static int fib(int n) throws Exception {
		if (n < 0)
			throw new Exception("参数不能为负！");
		else if (n == 0 || n == 1)
			return n;
		else
			return fib(n - 1) + fib(n - 2);
	}

	public static int fibonacci(int n, int acc1, int acc2) {
		if (n < 2)
			return acc1;
		else
			System.out.println("fibonacci(" + (n - 1) + "," + acc2 + "," + (acc2 + acc1) + ")");
		return fibonacci(n - 1, acc2, acc2 + acc1);
	}

	public static long factorial(int n) throws Exception {
		if (n < 0)
			throw new Exception("参数不能为负！");
		else if (n == 0 || n == 1)
			return 1;
		else
			return n * factorial(n - 1);
	}

	/**
	 * 关于 递归中 递推和回归的理解
	 * 
	 * @param n
	 */
	public static void recursion_display(int n) {
		int temp = n;// 保证前后打印的值一样
		System.out.println("递进:" + temp);
		if (n > 0) {
			recursion_display(--n);
		}
		System.out.println("回归:" + temp);
	}

}
