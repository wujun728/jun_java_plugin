package net.jueb.util4j.beta.classLoader.util;

import java.io.File;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.jar.JarFile;

public class UrlClassLoaderUtil extends ClassLoaderUtil{

	
	public UrlClassLoaderUtil(URLClassLoader loader) {
		super(loader);
	}
	
	@Override
	protected URLClassLoader getLoader() {
		return (URLClassLoader) super.getLoader();
	}
	
	
	/**
	 * 添加jar文件到当前加载器classPath
	 * @param jars jar文件集合
	 */
	public final int addJars(Set<JarFile> jars)
	{
		if(jars==null)
		{
			return 0;
		}
		int count=0;
		Iterator<JarFile> it=jars.iterator();
		while(it.hasNext())
		{
			if(addJar(it.next()))
			{
				count++;
			}
			
		}
		return count;
	}
	
	/**
	 * 添加jar文件到classPath
	 * 	URLClassLoader ul=(URLClassLoader) ClassLoader.getSystemClassLoader();
		UrlClassLoaderUtil ult=new UrlClassLoaderUtil(ul);
		System.out.println(ult.addJar(new File("d:/util4j.jar")));
		Class<?> c=Class.forName("net.jueb.serializable.nmap.type.NMap");
		System.out.println(c.getName());
		System.out.println(ult.getUrls().toString());
	 * @param jarFile
	 * @return
	 */
	public final boolean addJar(JarFile jarFile)
	{
		if(jarFile==null)
		{
			return false;
		}
		if(jarFile.getName().endsWith(".jar"))
		{
			URL url=null;
			try {
				url = new File(jarFile.getName()).toURI().toURL();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			if(!containsUrl(url))
			{
				return addURL(url);
			}
		}
		return false;
	}
	
	/**
	 * 加载class文件目录到classPath
	 * @param classFolder 该文件目录应该对应项目的bin或者改目录下即为包结构的根目录
	 * @return
	 */
	public final boolean addClassFolder(File classFolder)
	{
		if(classFolder!=null && classFolder.exists() && classFolder.isDirectory())
		{
			try {
				return addURL(classFolder.toURI().toURL());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	/**
	 * 添加一个classPath,可以是class目录,可以是jar文件，可以是class文件
	 * @param url
	 * @return
	 */
	public final boolean addURL(URL url)
	{
		if(containsUrl(url))
		{
			return false;
		}else
		{
			try {
				Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
				boolean accessible = method.isAccessible();
				try {
					if (accessible == false) {
						method.setAccessible(true);
					}
					// 设置类加载器
					URLClassLoader classLoader = getLoader();
					// 将当前类路径加入到类加载器中
					method.invoke(classLoader,url);
				} finally {
					method.setAccessible(accessible);
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	
	/**
	 * 是否已经存在资源路径
	 * @param url
	 * @return
	 */
	public final boolean containsUrl(URL url)
	{
		if(url==null)
		{
			return false;
		}
		URL[] urls=getLoader().getURLs();
		for(int i=0;i<urls.length;i++)
		{
			if(urls[i].equals(url))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取加载器的classPath路径
	 * @return
	 */
	public final Set<URL> getClassPath()
	{
		Set<URL> set=new HashSet<URL>();
		URL[] urls=getLoader().getURLs();
		for(int i=0;i<urls.length;i++)
		{
			set.add(urls[i]);
		}
		return set;
	}
}
