package com.java1234.chap03.sec06;


public class TestInner {

	public static void main(String[] args) {
		Outer outer=new Outer();
		Outer.Inner inner=outer.new Inner();
		inner.show();
	}
}
