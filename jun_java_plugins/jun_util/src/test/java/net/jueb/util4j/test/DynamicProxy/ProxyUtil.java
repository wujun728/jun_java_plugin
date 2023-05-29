package net.jueb.util4j.test.DynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtil {

	/**
	 * 创建代理对象
	 * @param loader
	 * @param proxyInterface
	 * @param proxyImpl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T newProxyInstance(ClassLoader loader,Class<T> proxyInterface,T proxyImpl) {
		return (T) Proxy.newProxyInstance(loader,
                new Class<?>[] { proxyInterface },
               new InvocationHandler() {
				@Override
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					return method.invoke(proxyImpl, args);
				}
                });
	}
	
	public static void main(String[] args) {
		Runnable run=new Runnable() {
			@Override
			public void run() {
				System.out.println("runImpl");
			}
		};
		Runnable run1=ProxyUtil.newProxyInstance(run.getClass().getClassLoader(),Runnable.class,run);
	}
}
