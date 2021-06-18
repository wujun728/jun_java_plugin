/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.powermock.PowermockTest.java
 * Class:			PowermockTest
 * Date:			2012-12-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.easymock.powermock;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;


/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-12-5 下午5:04:53 
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(HiUtil.class)
public class PowermockTest {
	/**
	 * 	PowerMock模拟范围
		PowerMock模拟几乎任何类的任何方法，一般以静态方法模拟为最多。
		PowerMock本身并不具备设定对象预期行为的功能，它是由EasyMock设定。
		静态方法模拟(Record)
		方式一：
		PowerMock.mockStatic(FacesContext.class);
		EasyMock.expect(FacesContext.getCurrentInstance()).andStubReturn(context);
	 * 描述
	 */
	@Test
	public void test() {
		PowerMock.mockStatic(HiUtil.class);
		EasyMock.expect(HiUtil.go("洛带")).andReturn("go 洛带");
		
		PowerMock.replayAll();
		
		String resultString = HiUtil.go("洛带");
		Assert.assertEquals("go 洛带", resultString);
		
		PowerMock.verifyAll();
		PowerMock.resetAll();
	}
}
