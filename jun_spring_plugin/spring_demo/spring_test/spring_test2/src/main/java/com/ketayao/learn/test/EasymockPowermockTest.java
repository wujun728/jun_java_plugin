/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.EasymockPowermockTest.java
 * Class:			EasymockPowermockTest
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
import org.powermock.api.easymock.PowerMock;

import com.ketayao.learn.test.mockito.powermock.HiUtil;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-12 上午11:22:58 
 */

public class EasymockPowermockTest {
	@Test
	public void test() {
		PowerMock.createMock(HiUtil.class);
		

		EasyMock.expect(HiUtil.go("xxx")).andReturn("Hi,YAO").times(1);
		
		PowerMock.replay(HiUtil.class);
		
		String resultString = HiUtil.go("xxx");
		Assert.assertEquals("Hi,YAO", resultString);
		
		PowerMock.verify(HiUtil.class);
	}
}
