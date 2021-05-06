package com.jun.plugin.annotation;

public class Test {

	public static void main(String[] args) {
		print();
	}

	public static void print() {

		IPhone4S i4s = new IPhone4S();
		System.out.println(i4s.getParameters());

	}
}
