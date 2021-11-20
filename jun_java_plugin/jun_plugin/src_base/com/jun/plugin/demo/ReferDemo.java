package com.jun.plugin.demo;

import java.lang.ref.WeakReference;

public class ReferDemo {
	public static void main(String[] args) {
		//声明A的对象，被wr引用，wr的弱引用
		WeakReference<A> wr = new WeakReference<A>(new A());//弱引用
		//获取引用中的数据
		
		
		System.gc();
		
		A a = wr.get(); //强引用
		
		System.err.println("over.."+a);
	}
}
class A{
	
	@Override
	public void finalize() throws Throwable {
		System.err.println("回收了");
	}
	
}
