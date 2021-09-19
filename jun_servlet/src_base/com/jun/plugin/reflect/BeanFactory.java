package com.jun.plugin.reflect;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Properties;

public class BeanFactory {

	// private static ResourceBundle bundle;
	private static Properties props = new Properties();
	static {
		// bundle = ResourceBundle.getBundle("instance");
		InputStream in = null;
		try {
			in = BeanFactory.class.getClassLoader().getResourceAsStream("com/jun/web/biz/factory/beanFactory.properties");
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}

	/*
	 * @SuppressWarnings("unchecked") public static <T> T getInstance(String
	 * key,Class<T> clazz) { String className = bundle.getString(key); try {
	 * return (T) Class.forName(className).newInstance(); } catch (Exception e)
	 * { throw new RuntimeException(e); } }
	 */

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(String className) {
		try {
			String className2 = props.getProperty(className);
			return (T) Class.forName(className2).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> clazz) {
		try {
			return (T) Class.forName(clazz.getName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> T getImpl(Class<T> clazz) {
		try {
			return (T) Class.forName(clazz.getName()).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		// Syserr obj=BeanFactory.getImpl("Syserr");
//		BizService obj = BeanFactory.getInstance(BizService.class);
	}
	
	/**
	 * 执行某对象方法
	 * @param owner  对象
	 * @param methodName  方法名
	 * @param args   参数
	 * @return 方法返回值
	 * @throws Exception
	 */
	public Object invokeMethod(Object owner, String methodName, Object... args)
	        throws Exception {
	    Class ownerClass = owner.getClass();
	    Class[] argsClass = null;
	    if (args == null) {
	        argsClass = new Class[0];
	    } else {
	        argsClass = new Class[args.length];
	    }
	    for (int i = 0, j = args.length; i < j; i++) {
	        argsClass[i] = args[i].getClass();
	    }
	    Method method = ownerClass.getMethod(methodName, argsClass);
	    return method.invoke(owner, args);
	}
	
	/*public static BizService getInstance2() {
		return (BizService) Proxy.newProxyInstance(BizService.class.getClass().getClassLoader(),
				BizService.class.getClass().getInterfaces(), new InvocationHandler() {
					public Object invoke(Object proxy, Method method, Object[] args)
							throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
						return method.invoke(BizService.class, args);
					}
				});

	}*/

	public static  <T> T  getInstance(String string, Class class1) {
		return null;
	}
}
