package com.jun.util;

import java.io.InputStream;
import java.util.Properties;

/**
 * Bean工厂类(不使用spring依赖注入，使用Java反射机制实现)
 * @author Wujun
 * @createTime   Jul 30, 2011 9:21:21 AM
 */
public class BeanFactory{
	private static Properties props = new Properties();
	
	static{
		InputStream in = null;
		try {
			in = BeanFactory.class.getClassLoader()
					.getResourceAsStream("com/lxw/oa/config/bean/beanFactory.properties");
			props.load(in);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} finally{
			if(null != in){
				try {
					in.close();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	/**
	 * 通过反射获取实例
	 * @param 接口的class
	 * @return 返回接口实现类
	 */
	public static <T>T getImpl(Class<T> clazz){
		try {
			String simpleName = clazz.getSimpleName();
			String className = props.getProperty(simpleName);
			return (T)Class.forName(className).newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
