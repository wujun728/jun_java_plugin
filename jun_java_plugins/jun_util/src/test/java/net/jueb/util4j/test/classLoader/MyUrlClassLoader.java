package net.jueb.util4j.test.classLoader;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public  class MyUrlClassLoader extends URLClassLoader{
	
	public MyUrlClassLoader() {
		super(new URL[]{});
	}
	
	/**
	 * 重写loadClass，不采用双亲委托机制("java."开头的类还是会由系统默认ClassLoader加载)
	 */
	@Override
	protected Class<?> loadClass(String className, boolean resolve)throws ClassNotFoundException {
		Class<?> clazz=null;
		//*******加载java基础类******
		if(className.startsWith("java"))
		{
			//如果类的包名为"java."开始，则有系统默认加载器AppClassLoader加载
			try {
				//得到系统默认的加载cl，即AppClassLoader
				 ClassLoader system = ClassLoader.getSystemClassLoader();
				 clazz = system.loadClass(className);
				 if (clazz != null) 
				 {
					 if (resolve)
					 {
						 resolveClass(clazz);
						 System.out.println("系统类加载器加载:"+clazz);
						 return (clazz);
					 }
				  }
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		//*******加载第三方类******
		clazz=super.loadClass(className, resolve);
		System.out.println(getClass().getName()+"加载器加载:"+clazz);
		return clazz;
	}
	public static void main(String[] args) throws MalformedURLException, ClassNotFoundException {
		
	}
	
}
