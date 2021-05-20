package com.itheima.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.itheima.service.BusinessService;
import com.itheima.service.impl.BusinessServiceImpl;
//AOP
public class BeanFactory {
	public static BusinessService getBusinessService(){
		final BusinessService s = new BusinessServiceImpl();
		
		BusinessService proxyS = (BusinessService)Proxy.newProxyInstance(s.getClass().getClassLoader(), 
				s.getClass().getInterfaces(), 
				new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws Throwable {
						try {
							TransactionManager.startTransaction();
							Object rtValue = method.invoke(s, args);
							return rtValue;
						} catch (Exception e) {
							TransactionManager.rollback();
							throw new RuntimeException(e);
						} finally {
							TransactionManager.commit();
							TransactionManager.release();
						}
					}
				});
		
		return proxyS;
	}
}
