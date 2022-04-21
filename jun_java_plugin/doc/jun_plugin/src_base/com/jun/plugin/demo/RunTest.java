package com.jun.plugin.demo;

public class RunTest {
	/**
	 * 如果在使用时，不指定给哪一个属性，则默认就是给value属性
	 */
	@MyTest("Jack")
	public void abc(){
		System.err.println("OKOKOabc....");
	}
	
	@MyTest(value="Rose")
	private void aaaa(){
		System.err.println("aaaaaa....");
	}
	/**
	 * 如果给多个值，则每个值，都要指定属性的名称
	 */
	@MyTest("AAAA")
	public void bbb(){
		
	}
}
