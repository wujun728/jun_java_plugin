package com.ky26.object;

import java.util.Arrays;

public class Test2 {
	public static void main(String[] args) {
		int a=3;
		int b=4;
		change(a,b);//进栈——执行完毕——弹栈
		System.out.println("a:"+a);
		System.out.println("b:"+b);//传递的值不会影响原值
		
		int[] count={1,2,3,4,5};
		change(count);//参数counts和count都是一个地址，同时指向堆中的对象，，改变了其中一个的值则另一个也会改变；
		System.out.println("调用方法之后的数组："+Arrays.toString(count));//引用的值改变了原值
	}
	static void change(int i,int j){
		int temp=i;
		i=j;
		j=temp;
	}
	
	public static void change(int[] counts){
		counts[0]=6;
		System.out.println("count的第一个数组元素是："+counts[0]);
	}
}
/*在Java中参数传递都是有副本的（或者说按值传递），都是将参数复制一份再进行传递，基本数据类型的值是复制，对两份数据进行操作，因此不会改变原值；
另一种解释是，基本数据类型存储在栈中，当调用方法结束时方法弹栈，则不能改变原值（找不到原值）；
对象的传递是传递地址，是对象的引用，两个地址指向堆内存中的同一份数据，改变了其中一个则两个都发生变化；*/


