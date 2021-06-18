/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.easymock.EasyMockTest.java
 * Class:			EasyMockTest
 * Date:			2012-12-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.easymock;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-5 下午4:36:59 
 */

public class EasyMockTest {
	
	/**
	 * 对象模拟(Record)，模拟对象有两种方式 
	 * 描述
	 */
	@Test
	public void test() {
		//方式一：
		Hi hi = EasyMock.createMock(Hi.class);
		
		//方式二：
		IMocksControl control = EasyMock.createControl();
		Hi hi2 = control.createMock(Hi.class);
	}
	
	/**
	 * 	设定方法预期行为
		设定方法预期行为是指对模拟的目标方法设定期望的结果
		语法：
		有返回值：EasyMock.expect(Mock对象.方法名(方法参数列表).andReturn(预期返回值).times(执行次数);
		Void方法： Mock对象.方法名(方法参数列表);
		                  EasyMock.expectLastCall().andReturn(预期返回值) .times(执行次数);
		默认不设置times执行次数为1
	 * 描述
	 */
	@Test
	public void test2() {
		Hi hi = EasyMock.createMock(Hi.class);
		EasyMock.expect(hi.sayHi("yao")).andReturn("Hi,yao").times(1);
		
		//hi.sayHi("yao");
		
		//EasyMock.verify(hi);
	}
	
	/**
	 * 	验证预期行为
		验证方法是否被调用及调用次数是否正确
		语法：EasyMock.verify(Mock对象);
		示例：EasyMock.verify(commonService1);
	 * 描述
	 */
	@Test
	public void test3() {
		Hi hi = EasyMock.createMock(Hi.class);
		EasyMock.expect(hi.sayHi("yao")).andReturn("Hi,yao").times(1);
		EasyMock.verify(hi);
	}
	
	/**
	 * 	重置
		将Mock对象重新初始化，方便再次设置预期行为
		语法：EasyMock.reset(Mock对象);
		示例：EasyMock.reset(commonService1);
	 * 描述
	 */
	@Test
	public void test4() {
		Hi hi = EasyMock.createMock(Hi.class);
		EasyMock.reset(hi);
	}
	
	/**
	 * 录制->回放->调用实际方法->验证->重置
	 * 描述
	 */
	@Test
	public void test5() {
		Hi hi = EasyMock.createMock(Hi.class);
		EasyMock.expect(hi.sayHi("yao")).andReturn("Hi,yao").times(1);
		
		EasyMock.replay(hi);
		
		String resultString = hi.sayHi("yao");
		Assert.assertEquals("Hi,yao", resultString);
		
		//EasyMock.expectLastCall();
		
		EasyMock.verify(hi);
		EasyMock.reset(hi);
	}
}
