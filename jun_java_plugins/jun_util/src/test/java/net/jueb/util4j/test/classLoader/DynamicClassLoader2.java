package net.jueb.util4j.test.classLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class DynamicClassLoader2 extends DynamicClassLoader {
	
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
        return defineClass(className, raw); 
    }
	
	public static void main(String[] args) throws Exception {
		String path="C:\\Users\\juebanlin\\git\\util4j\\util4j\\target\\test-classes";
		DynamicClassLoader2 u=new DynamicClassLoader2();
		Class c=u.defineClassByDir(path, "net.jueb.util4j.test.classLoader.TestScript2");
		TestScript i=(TestScript) c.newInstance();
		Runnable r=new Runnable() {
			
			@Override
			public void run() {
				Thread t=Thread.currentThread();
				System.out.println(t);
			}
		};
		i.run(new Runnable() {
			@Override
			public void run() {
				new Thread(r).start();
			};
		});
		Thread.sleep(10000000l);
	}
}