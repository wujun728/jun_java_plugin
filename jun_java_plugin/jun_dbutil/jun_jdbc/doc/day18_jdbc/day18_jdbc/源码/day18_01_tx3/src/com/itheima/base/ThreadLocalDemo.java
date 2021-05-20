package com.itheima.base;

public class ThreadLocalDemo {
	public static void main(String[] args) {
		ThreadLocal tl = new ThreadLocal();
		Thread1 t1 = new Thread1(tl);
		t1.start();
		tl.set("p");// map.put(mainœﬂ≥Ã,"p");
		System.out.println("main:"+tl.get());
	}
}
