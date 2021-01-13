package com.java1234.chap06.sec03;

import com.java1234.chap06.sec02.Cat;
import com.java1234.chap06.sec02.Demo;
import com.java1234.chap06.sec02.Dog;

public class Test {

	/**
	 * Í¨Åä·û·ºÐÍ
	 * @param a
	 */
	private static void take(Demo<?> a){
		a.print();
	}
	
	public static void main(String[] args) {
		Demo<Dog> demo=new Demo<Dog>(new Dog());
		take(demo);
		
		Demo<Cat> demo2=new Demo<Cat>(new Cat());
		take(demo2);
	}
}
