package net.jueb.util4j.test.classLoader;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DynamicClassLoader extends ClassLoader {
	protected Logger log=LoggerFactory.getLogger(getClass());
	
	
	public DynamicClassLoader() {
		super(Thread.currentThread().getContextClassLoader());// 解决在webapp里面支持热更新脚本
	}

	/**
	 * 根据字节数组定义一个class对象,成功则记录
	 * @param className
	 * @param raw
	 * @return
	 */
	public Class<?> defineClass(String className,byte[] raw)
	{
		return defineClass(className,raw,0,raw.length);
	}
	
	/**
	 * 加载类,如果是系统类则交给系统加载 如果当前类已经加载则返回类 如果当前类没有加载则定义并返回
	 */
	@Override
	protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
		Class<?> clazz = null;
		// 查找当前类加载中已加载的
		if (clazz == null) {
			clazz = findLoadedClass(className);
		}
		// 当前jar中加载
		if (clazz == null) {
			// 查找当前类加载器urls或者当前类加载器所属线程类加载器
			try {
				clazz = findClass(className);
			} catch (Exception e) {
			}
		}
		if (clazz == null) {// 系统类加载
			try {
				clazz = findSystemClass(className);
			} catch (Exception e) {

			}
		}
		String ClassLoader = null;
		if (clazz != null) {// 解析类结构
			ClassLoader = "" + clazz.getClassLoader();
			if (resolve) {
				resolveClass(clazz);
			}
		}
		log.trace("loadClass " + (clazz == null) + ":" + className + ",resolve=" + resolve + ",Clazz=" + clazz+ ",ClassLoader=" + ClassLoader);
		return clazz;
	}

	/**
	 * 查找类,这个方法一般多用于依赖类的查找,如果之前已经加载过,则重复加载会报错,所以需要添加findLoadedClass判断
	 */
	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		Class<?> clazz = findLoadedClass(name);
		if (clazz != null) {
			return clazz;
		}
		return super.findClass(name);
	}
	
	@Override
	protected URL findResource(String name) {
		return super.findResource(name);
	}
	
	@Override
	protected String findLibrary(String libname) {
		return super.findLibrary(libname);
	}
	
	@Override
	protected Enumeration<URL> findResources(String name) throws IOException {
		return super.findResources(name);
	}
}