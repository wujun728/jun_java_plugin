/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.EasymockTest.java
 * Class:			EasymockTest
 * Date:			2012-12-12
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test;

import junit.framework.Assert;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.ketayao.learn.test.easymock.Hi;
import com.ketayao.learn.test.easymock.powermock.HiUtil;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-12 上午11:07:00 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(HiUtil.class)
public class EasymockTest {
	@Test
	public void test() {
		Hi hi = EasyMock.createMock(Hi.class);
		EasyMock.expect(hi.sayHi("YAO")).andReturn("Hi,YAO").times(1);
		
		EasyMock.replay(hi);
		
		String resultString = hi.sayHi("YAO");
		Assert.assertEquals("Hi,YAO", resultString);
		
		EasyMock.verify(hi);
		
		EasyMock.reset(hi);
		
		EasyMock.expect(hi.sayHi("BO")).andReturn("Hi,BO").times(2);
		EasyMock.replay(hi);
		resultString = hi.sayHi("BO");
		resultString = hi.sayHi("BO");
		EasyMock.verify(hi);
	}
	
}
