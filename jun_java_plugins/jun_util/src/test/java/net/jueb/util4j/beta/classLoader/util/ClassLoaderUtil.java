package net.jueb.util4j.beta.classLoader.util;

import java.io.InputStream;
import java.net.URL;

public class ClassLoaderUtil extends ClassUtil{
	private final ClassLoader loader;
	
	public ClassLoaderUtil(ClassLoader loader) {
		this.loader=loader;
	}
	
	protected ClassLoader getLoader()
	{
		return this.loader;
	}
	
	/**
	 * 根据当前classPath返回对应类名的类文件字节输入流
	 * @param className
	 * @return
	 */
	public InputStream getClassStream(String className)
	{
		String name=className.replace('.','/')+".class";
		return getLoader().getResourceAsStream(name);
	}
	
	/**
	 * 根据class类名获取class的访问路径
	 * 参数为:net.jueb.serializable.nmap.type.NMap
	 * 则返回：jar:file:/d:/util4j.jar!/net/jueb/serializable/nmap/type/NMap.class
	 * @param className
	 * @return
	 */
	public URL getClassUrl(String className)
	{
		String name=className.replace('.','/')+".class";
		return getLoader().getResource(name);
	}
	
}
