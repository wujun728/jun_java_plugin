package com.java1234.chap06.sec01;

/**
 * 定义泛型类
 * @author caofeng
 *
 * @param <T>
 */
public class CC<T>{

	private T ob;

	
	public CC(T ob) {
		super();
		this.ob = ob;
	}

	public T getOb() {
		return ob;
	}

	public void setOb(T ob) {
		this.ob = ob;
	}
	
	/**
	 * 打印T的类型
	 */
	public void print(){
		System.out.println("T的实际类型是："+ob.getClass().getName());
	}
	
}
