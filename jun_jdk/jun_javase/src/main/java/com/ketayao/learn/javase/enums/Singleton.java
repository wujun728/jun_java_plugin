package com.ketayao.learn.javase.enums;

public class Singleton {
	// 构造函数私有，只限内部调用
	private Singleton() {
	};

	private static Singleton instance = null;

	public static synchronized Singleton getInstance() {
		if (instance == null)
			instance = new Singleton();
		return instance;
	}
}