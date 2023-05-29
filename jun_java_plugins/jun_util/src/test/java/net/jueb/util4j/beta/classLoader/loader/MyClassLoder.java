package net.jueb.util4j.beta.classLoader.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义类加载器
 * @author Administrator
 *打破双亲委托加载顺序,让子类先加载,父类后加载
 */
public class MyClassLoder extends ClassLoader {
	protected Logger log = LoggerFactory.getLogger(getClass());
	
	private boolean useSystem=true;
    
	public MyClassLoder() throws IOException {
		super(null);
	}
	
	public void useSystem(boolean use)
	{
		this.useSystem=use;
	}
	
	public boolean useSystem()
	{
		return useSystem;
	}
	/**
	 * @param  classNames-(二进制名称)有效类名称的示例包括： "java.lang.String"
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void defineClassByDir(String basedir,String[] classNames) throws IOException
	{
		for (int i = 0; i < classNames.length; i++) 
		{ 
			defineClassByDir(basedir,classNames[i]); 
        } 
	}
	/**
	 * 根据类名在基本目录中加载class文件
	 * @param className-(二进制名称)有效类名称的示例包括： "java.lang.String"
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public Class<?> defineClassByDir(String basedir,String className) throws IOException
	{ 
        Class<?> cls = null; 
        StringBuffer sb = new StringBuffer(basedir); 
        String classFname = className.replace('.', File.separatorChar) + ".class";
        sb.append(File.separator + classFname); 
        File classFile = new File(sb.toString()); 
        cls = defineClassByFile(className,classFile);
        return cls; 
    } 
	
	/**
	 * 将一个 classFile读取的byte 数组转换为 Class 类的实例。
	 * @param className-(二进制名称)有效类名称的示例包括： "java.lang.String"
	 * @param classFile class文件
	 * @return
	 * @throws IOException
	 */
	private Class<?> defineClassByFile(String className,File classFile) throws IOException
	{ 
		FileInputStream fis=new FileInputStream(classFile);
		long len=classFile.length();
        byte[] raw = new byte[(int) len]; 
        fis.read(raw); 
        fis.close(); 
        return defineClassByBytes(className, raw); 
    }
	
	/**
	 * 根据字节数组定义一个class对象,成功则记录
	 * @param className
	 * @param raw
	 * @return
	 */
	private Class<?> defineClassByBytes(String className,byte[] raw)
	{
		System.out.println("InputStream:"+getResource(className));
		
		Class<?> cls = null; 
		cls=defineClass(className,raw,0,raw.length);
		if(cls!=null)
		{
			System.out.println("Class:"+className+"被定义!");
		}
		return cls;
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
	
	@Override
	protected Class<?> findClass(String className) throws ClassNotFoundException {
		syso("当前类加载器classpath中查找并定义类:"+className);
		Class<?> clazz=null;
		byte[] bytes=findClassFileBytes(className);
		if(bytes!=null)
		{
			clazz=defineClass(className,bytes, 0, bytes.length);
		}
		if(clazz==null)
		{
			throw new ClassNotFoundException();
		}
		return clazz;
	}
	
	/**
	 * 根据类名获取类文件字节
	 * @param className
	 * @return
	 */
	public byte[] findClassFileBytes(String className)
	{
		//TODO
		return null;
	}
}

