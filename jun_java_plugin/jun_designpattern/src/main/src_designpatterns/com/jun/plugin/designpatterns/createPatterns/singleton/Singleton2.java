package com.jun.plugin.designpatterns.createPatterns.singleton;

public class Singleton2 {
	
	private String username;

	private void Singelton2() {

	}
	private static Singleton2 singleton=new Singleton2();
	
	public static Singleton2 getInstance(){
		return singleton;
	}
	public static void main(String[] args) {
		int i=0;
//		Singleton2.getInstance().username="abc";
		while(i<10){
			System.err.println(Singleton2.getInstance());
			i++;
		}
	}

}
