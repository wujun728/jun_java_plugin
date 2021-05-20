package com.java1234.chap05.sec02;

public class TestStringBuffer {

	public static void main(String[] args) {
		StringBuffer sb=new StringBuffer("123");
		sb.append("abc");
		System.out.println(sb.toString());
	}
}
