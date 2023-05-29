package com.jun.plugin.util4j.hotSwap.classProvider;

import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义类加载器,默认使用当前线程的类加载器作为父加载器
 * 此类加载器可以加载jar,目录下的class文件,http以及ftp等class资源
 * 注意:
 * 1.加载的class如果被引用则不能回收classloader
 * 2.加载的class如果创建了线程,则线程的AccessControllerContext的ProtectionDomain会持有此class的classLoader,
 * 导致classLoader不能回收,(比如加载的class创建了mysql连接池)
 * @author juebanlin
 */
public class DynamicClassLoader extends URLClassLoader {
	protected Logger log = LoggerFactory.getLogger(getClass());

	public DynamicClassLoader() {
		this(new URL[] {}, Thread.currentThread().getContextClassLoader());
	}

	public DynamicClassLoader(URL url) {
		this(new URL[] { url }, Thread.currentThread().getContextClassLoader());
	}

	public DynamicClassLoader(ClassLoader parent) {
		this(new URL[] {}, parent);
	}

	public DynamicClassLoader(URL[] urls, ClassLoader parent) {
		super(urls, parent);
	}

	/**
	 * 加载类,如果是系统类则交给系统加载 如果当前类已经加载则返回类 如果当前类没有加载则定义并返回
	 */
	@Override
	protected Class<?> loadClass(String className, boolean resolve) throws ClassNotFoundException {
		Class<?> clazz= null;
		try {
			clazz = findClass(className);//查找已经加载过的类或者待加载的类
		} catch (Exception e) {
		}
		if (clazz == null) {// 系统类加载
			try {
				clazz = findSystemClass(className);
			} catch (ClassNotFoundException e) {
			}
		}
		if (clazz == null) {// 上层加载器加载
			ClassLoader parent=getParent();
			if(parent!=null)
			{
				try {
					clazz = parent.loadClass(className);
				} catch (Exception e) {
				}
			}
		}
		// 查找当前类加载中已加载的
		if (clazz == null) {
			throw new ClassNotFoundException("className:"+className+",resolve:"+resolve);
		}
		String classLoader = "" + clazz.getClassLoader();
		if (resolve) {// 解析类结构
			resolveClass(clazz);
		}
		log.trace("loadClass " + (clazz == null) + ":" + className + ",resolve=" + resolve + ",Clazz=" + clazz+ ",ClassLoader=" + classLoader);
		return clazz;
	}

	/**
	 * 查找类,这个方法一般多用于依赖类的查找,如果之前已经加载过,则重复加载会报错,所以需要添加findLoadedClass判断
	 */
	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		log.trace("findClass:" + name);
		Class<?> clazz = findLoadedClass(name);
		if (clazz != null) {
			return clazz;
		}
		return super.findClass(name);
	}

	@Override
	protected void addURL(URL url) {
		super.addURL(url);
	}
}