package com.jun.plugin.demo;
import java.net.URL;

import org.junit.Test;
public class ReadFile1 {
	/**
	 * 一个类的加载分两种情况
	 * 
	 * 第一种是java项目
	 * 		在java项目中由于没有容器，只有JVM,所以只有三个类加载器(...)
	 * 		所以在java项目中所有用户类都是被AppClassLoader（用java写的）加载的。
	 * 		而所有如String这样的系统类，都是被BootStrap(用C语言写)加载的。
	 * 
	 * 第二种是web项目	
	 * 		如果一个类运行在容器如tomat中，由于tomcat已经扩展了类加载器
	 * 		WebAppClassLoader,StandardClassLoader
	 * 		所以所有的运行在tomcat中的类都是被Stan..加载的。
	 * 		
	 * 		所有的系统类如java.lang.String,ClassLoader不管是运行在java项目中
	 * 		还是运行在tomcat中都永远是被BootStrap加载的。
	 * 
	 */
	
	@Test
	public void abc(){
		
		//在tomcat中所有类都是被tomcat加载的
		ClassLoader cl = ReadFile.class.getClassLoader();
		System.err.println(cl.getClass());//在java项目中是AppClassLoader
		URL url = cl.getResource("a.txt");
		System.err.println("url1 is:"+url.getPath());//在web项目中将会是c:/tomcast...
		
		System.err.println("==================================");
	
	}
	
}
