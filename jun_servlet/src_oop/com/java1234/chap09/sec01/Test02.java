package com.java1234.chap09.sec01;

public class Test02 {

	public static void main(String[] args) {
		Music musicThread=new Music();
		Eat eatThread=new Eat();
		musicThread.start();
		eatThread.start();
	}
}
