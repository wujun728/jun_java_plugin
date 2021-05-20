/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.junit4.Junit4FixtureTest.java
 * Class:			Junit4FixtureTest
 * Date:			2012-12-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.junit4;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/** 
 * 	Fixture ：它是指在执行一个或者多个测试方法时需要的一系列公共资源或者数据，例如测试环境，测试数据等等。
	Fixture分为两种：方法级Fixture和类级Fixture.

	方法级Fixture：同一测试类中的所有测试方法都可以共用它来初始化 Fixture 和注销 Fixture。 
	类级Fixture：仅会在测试类中所有测试方法执行之前执行初始化Fixture，并在全部测试方法测试完毕之后执行注销方法Fixture。
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-5 下午4:29:45 
 */

public class Junit4FixtureTest {
	/**
	 * 类级的Fixture注销设置，使用注解BeforeClass,设置方法必须是static的，并且是public的。而且没有返回值。
	 * 描述
	 */
	@BeforeClass
	public static void before() {
		System.out.println("--before--");
	}
	
	@Test
	public void test() {
		System.out.println("==test==");
		Assert.assertEquals("Hello", "Hello");
	}
	
	/**
	 * 类级的Fixture注销设置，使用注解AfterClass,设置方法必须是static的，并且是public的。而且没有返回值。
	 * 描述
	 */
	@AfterClass
	public static void after() {
		System.out.println("--after--");
	}
}
