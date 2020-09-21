package com.us.demo.test;

import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String []args){
		List list=new ArrayList();
		list.add(1);
		list.add("Hello");
		TestObject test2= new TestObject("Test1");
		list.add(test2);
		list.add(test2);
		System.out.println(list.size());
		System.out.println(list.get(0).toString());
		System.out.println(list.get(1).toString());
		System.out.println(list.get(2).toString());
		System.out.println(list.get(3).toString());

	}
//	public class TestObject{
//		public String name;
//		public TestObject(){
//			
//		}
//		public TestObject(String name){
//			this.name=name;
//		}
//	}

}
