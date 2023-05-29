package net.jueb.util4j.beta.classLoader.util;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 用于获取指定包名下的所有类名.<br/>
 * 并可设置是否遍历该包名下的子包的类名.<br/>
 * 并可通过Annotation(内注)来过滤，避免一些内部类的干扰.<br/>
 * 
 * @author Sodino E-mail:sodino@qq.com
 * @version Time：2014年2月10日 下午3:55:59
 */
public class ClassUtil {
	
	public static final void TestUrl() throws Exception
	{
		 // 创建指向jar文件的URL
	      URL url = new URL("jar:http://hostname/my.jar!/");
	      // 创建指向文件系统的URL
	      url = new URL("jar:file:/c:/almanac/my.jar!/");
	      // 读取jar文件
	      JarURLConnection conn = (JarURLConnection) url.openConnection();
	      JarFile jarfile = conn.getJarFile();
	      // 如果URL没有任何入口，则名字为null
	      String entryName = conn.getEntryName(); // null
	      // 创建一个指向jar文件里一个入口的URL
	      url = new URL("jar:file:/c:/almanac/my.jar!/com/mycompany/MyClass.class");
	      // 读取jar文件
	      conn = (JarURLConnection) url.openConnection();
	      jarfile = conn.getJarFile();
	      // 此时的入口名字应该和指定的URL相同
	      entryName = conn.getEntryName();
	      // 得到jar文件的入口
	      JarEntry jarEntry = conn.getJarEntry();
	}
	
	/**
	 * 获取所在项目下的bin目录
	 * @return
	 */
	public static final File getProjectBin()
	{
		File bin=new File("bin");
		if(bin.exists() && bin.isDirectory())
		{
			return bin;
		}
		return null;
	}
	/**
	 * new File(ClassLoader.getSystemResource(".").getFile())
	 * @return
	 */
	public static final File getProjectBin2()
	{
		return new File(ClassLoader.getSystemResource(".").getFile());
	}
	
	/**
	 * 获取第三方class类所在目录地址
	 * @param clazz
	 * @return
	 */
	public static final URL getClassDir(Class<?> clazz)
	{
		return clazz.getResource("");
	}
	
	/**
	 * 获取第三方class类的文件形式
	 * @param clazz
	 * @return
	 */
	public static final File getClassFile(Class<?> clazz)
	{
		return new File(getClassDir(clazz).getFile()+clazz.getSimpleName()+".class");
	}
	
	public static String getRootDic() {
		return System.getProperty("user.dir");
	}
}