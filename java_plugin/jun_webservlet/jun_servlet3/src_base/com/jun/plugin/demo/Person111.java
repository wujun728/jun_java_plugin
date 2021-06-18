package com.jun.plugin.demo;
public class Person111 {
	static{
		System.err.println("类被加载了");
	}
	public Person111() {
		System.err.println("类被实例化了");
	}
	public void say(){
		System.err.println("Hello");
	}
	@Override
	public String toString() {
		return "new ....这是person\t"+this.hashCode();
	}
}
