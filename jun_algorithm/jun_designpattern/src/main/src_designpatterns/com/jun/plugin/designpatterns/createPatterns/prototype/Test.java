package com.jun.plugin.designpatterns.createPatterns.prototype;

public class Test {

	public static void main(String[] args) {
		Prototype p1=new Prototype(" p1 	");
		Prototype p2=(Prototype) p1.clone();
		System.err.println(p1.getName());
		System.err.println(p2.getName());
	}

}
