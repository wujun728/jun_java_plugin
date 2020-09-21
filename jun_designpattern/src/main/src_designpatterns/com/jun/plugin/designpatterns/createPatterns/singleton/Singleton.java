package com.jun.plugin.designpatterns.createPatterns.singleton;

public class Singleton {

	private static Singleton singleton = null;

	@SuppressWarnings("unused")
	private void Singelton() {

	}

	public static Singleton getInstance() {
		return singleInit();
	}

	private synchronized static Singleton singleInit() {
		if (singleton == null) {
			singleton = new Singleton();
		}
		return singleton;
	}

	public static void main(String[] args) {
		int i = 0;
		while (i < 10) {
			System.err.println(Singleton.getInstance());
			i++;
		}
	}

}
