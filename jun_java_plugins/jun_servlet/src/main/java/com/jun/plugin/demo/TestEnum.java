package com.jun.plugin.demo;

enum Color{
	RED,GREEN,YELLOW,BLACK,BLUE
}

public class TestEnum {
	public static void main(String[] args) {
		System.out.println(Color.GREEN.ordinal());
		System.out.println(Color.GREEN.toString());
	}
}


