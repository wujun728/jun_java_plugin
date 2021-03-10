/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.junit4.Junit4Test.java
 * Class:			Junit4Test
 * Date:			2012-12-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.junit4;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/** 
 * 	
 * 	1.测试方法必须使用注解 org.JUnit4.Test 修饰。
	2.测试方法必须使用 public void 修饰，而且不能带有任何参数。  
	3.测试方法里面写断言方法。
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-5 下午4:25:41 
 */

public class Junit4Test {
	@Before
	public void before() {
		System.out.println("--before--");
	}
	
	@Test
	public void test() {
		System.out.println("==test==");
		Assert.assertEquals("Hello", "Hello");
	}
	
	@After
	public void after() {
		System.out.println("--after--");
	}
}
