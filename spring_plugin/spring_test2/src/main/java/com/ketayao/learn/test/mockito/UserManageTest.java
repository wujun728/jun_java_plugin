/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.test.mockito.UserManageTest.java
 * Class:			UserManageTest
 * Date:			2012-12-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package com.ketayao.learn.test.mockito;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/** 
 * 使用注解
 * @author Wujun
 * Version  1.1.0
 * @since   2012-12-6 上午11:06:41 
 */

public class UserManageTest {
	@InjectMocks
	private UserManage userManage;
	
	@Mock
	private UserManage userManage2;
	
	@Mock
	private UserDao userDao;
	
	private User user;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		user = new User();
		user.setId(1);
		user.setName("yao");
		user.setPassword("123456");
	}
	
	/**
	 * stub函数
	 * 描述
	 */
	@Test
	public void test() {
		Mockito.when(userDao.get(1)).thenReturn(user);
		
		User resultUser = userManage.getUser(1);
		assertEquals(new Integer(1), resultUser.getId());
	}
	
	/**
	 * 如果要设定没返回值函数抛异常，不再需要easyMock那种奇怪的expectLastCall了，直接写
	 * 描述
	 */
	@Test
	public void test2() {
		Mockito.when(userDao.get(1)).thenReturn(user);
		
		User resultUser = userManage.getUser(1);
		Mockito.doThrow(new RuntimeException()).when(userManage2).printUser(resultUser);
		
		try {
			userManage2.printUser(resultUser);
		} catch (RuntimeException e) {
			Assert.assertEquals(RuntimeException.class, e.getClass());
		}
	}
	
	/**
	 * Stub的最高级境界之一，使用Answer接口，计算输入参数来决定返回值
	 * 描述
	 */
	public void test3() {
		Mockito.when(userDao.get(1)).thenAnswer(new Answer<User>() {

			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				Integer id = (Integer)args[0];
				if (id.equals(1)) {
					return user;
				} 
				
				user.setId(id);
				return user;
			}
			
		});
		
		User resultUser = userManage.getUser(1);
		assertEquals(new Integer(1), resultUser.getId());
		
		User resultUser2 = userManage.getUser(2);
		assertEquals(new Integer(2), resultUser2.getId());
	}
	
	/**
	 * Stub的最高级境界之二，spy 真实Object，只stub其中部分的方法，其他方法继续真实的跑。
	 * 描述
	 */
	@Test
	public void test4() {
		List<String> list = new LinkedList<String>();
		List<String> spy = Mockito.spy(list);
			 
		//optionally, you can stub out some methods:
		Mockito.when(spy.size()).thenReturn(100);
		//using the spy calls *real* methods
		spy.add("one");
		
		Assert.assertEquals(100, spy.size());
	}
	
	/**
	 * verfiy函数:
	 * 描述
	 */
	@Test
	public void test5() {
		Mockito.when(userDao.get(1)).thenReturn(user);
		
		User resultUser = userManage.getUser(1);
		
		Mockito.verify(userDao).get(Mockito.anyInt());
		Mockito.verify(userDao, Mockito.never()).get(2);
	}
	
	/**
	 * verify的最高境界之一，使用capture()把输入参数记下来再慢慢判断。
	 * 描述
	 */
	@Test
	public void test6() {
		Mockito.when(userDao.get(1)).thenReturn(user);
		ArgumentCaptor<Integer> argument = ArgumentCaptor.forClass(Integer.class);
		
		User resultUser = userManage.getUser(1);
		
		Mockito.verify(userDao).get(argument.capture());
		assertEquals(new Integer(1), argument.getValue());
	}
}
