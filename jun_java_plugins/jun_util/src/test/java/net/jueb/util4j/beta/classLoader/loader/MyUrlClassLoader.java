package net.jueb.util4j.beta.classLoader.loader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *@author juebanlin
 *@email juebanlin@gmail.com
 *@createTime 2015年4月12日 下午2:56:45
 *打破双亲委托加载顺序,让子类先加载,父类后加载
 **/
public class MyUrlClassLoader extends URLClassLoader{
	protected Logger log = LoggerFactory.getLogger(getClass());
	private boolean useSystem=true;
	
	
	public void useSystem(boolean use)
	{
		this.useSystem=use;
	}
	
	public boolean useSystem()
	{
		return useSystem;
	}
	
	public MyUrlClassLoader() {
		super(new URL[]{},null);
	}
	
	public MyUrlClassLoader(URL[] urls) {
		super(urls,null);
	}
	
	public final void addURL(URL url)
	{
		if(url!=null)
		{
			super.addURL(url);
		}
	}
	
	public final void addURL(URL[] urls)
	{
		if(urls!=null)
		{
			for(URL url:urls)
			{
				addURL(url);
			}
		}
	}
	
	
	/**
	 * 加载类,如果是系统类则交给系统加载
	 * 如果当前类已经加载则返回类
	 * 如果当前类没有加载则定义并返回
	 */
	@Override
	protected Class<?> loadClass(String className, boolean resolve)
			throws ClassNotFoundException {
		Class<?> clazz=null;
		if(className.startsWith("java.")||className.startsWith("javax."))
		{//如果是系统类加载器
			clazz=findSystemClass(className);
			if (clazz != null) 
			{//解析类结构
				syso("系统类加载器加载:"+className+"完成!");
			}
		}
		//查找当前类加载中已加载的
		if (clazz == null) 
		{//解析类结构
			clazz=findLoadedClass(className);
			if(clazz!=null)
			{
				syso(getClass()+"加载:"+className+"完成!");
			}
		}
		if(clazz==null)
		{
			//查找当前类加载器urls或者当前类加载器所属线程类加载器
			try {
				clazz=findClass(className);
				if(clazz!=null)
				{
					syso(getClass()+"加载:"+className+"完成!");
				}
			} catch (Exception e) {
				if(useSystem)
				{
					//如果该类没有加载过，并且不属于必须由该类加载器加载之列都委托给系统加载器进行加载。
					ClassLoader loader=Thread.currentThread().getContextClassLoader();
					if(loader!=this)
					{//如果当前线程上下文的加载器不是自己,用请求委托加载
						clazz=loader.loadClass(className);
					}
					if(clazz!=null)
					{
						syso(loader.getClass().getName()+"加载:"+className+"完成!");
					}else
					{
						//查找系统类加载器
						clazz=findSystemClass(className);
						syso("系统类加载器加载:"+className+"完成!");
					}
				}
			}
		}
		if (clazz != null) 
		{//解析类结构
			if (resolve)
				resolveClass(clazz);
		}
		return clazz;
	}
	
	protected void syso(String log)
	{
		if(this.log!=null)
		{
			this.log.debug(log);
		}else
		{
			System.out.println(log);
		}
	}
	/**
	 * 查找url路径列表中类文件并声明定义类
	 */
	@Override
	protected Class<?> findClass(final String name) throws ClassNotFoundException {
		return super.findClass(name);
	}
}
