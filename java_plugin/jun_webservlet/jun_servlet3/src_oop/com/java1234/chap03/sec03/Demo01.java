package com.java1234.chap03.sec03;

public class Demo01 {

	int add(int a,int b){
		System.out.println("方法一");
		return a+b;
	}
	
	/**
	 * 方法的重载，参数个数不一样
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	int add(int a,int b,int c){
		System.out.println("方法二");
		return a+b+c;
	}
	
	/**
	 * 方法的重载，参数的类型不一样
	 * @param a
	 * @param b
	 * @return
	 */
	int add(int a,String b){
		System.out.println("方法三");
		return a+Integer.parseInt(b);
	}
	
	/**
	 * 参数类型个数一样，返回值类型不一样，不算重载，直接会报错的  会说方法重名
	 * @param args
	 */
	/*long add(int a,int b){
		return a+b;
	}*/
	
	public static void main(String[] args) {
		Demo01 demo=new Demo01();
		System.out.println(demo.add(1, 2));
		System.out.println(demo.add(1, 2, 3));
		System.out.println(demo.add(1, "3"));
		
		/*int m=demo.add(1, 2);
		long n=demo.add(1, 3);
		
		demo.add(1, 3);*/
	}
}
