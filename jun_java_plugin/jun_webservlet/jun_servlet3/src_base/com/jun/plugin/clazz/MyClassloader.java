package com.jun.plugin.clazz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class MyClassloader extends ClassLoader {

	private String path;

	public MyClassloader(String path) {
		this.path = path;

	}

	@Override
	protected Class<?> findClass(String classname) throws ClassNotFoundException {

		Class clazz = null;

		String classFile = getClassFile(classname);

		try {
			FileInputStream fis = new FileInputStream(classFile);
			File file = new File(classFile);
			long len = file.length();
			byte[] raw = new byte[(int) len];

			fis.read(raw);

			clazz = super.defineClass(classname, raw, 0, raw.length);

			fis.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return clazz;
	}

	private String getClassFile(String classname) {
		// D:\share\JavaProjects\cms\Test\bin \ +classname
		StringBuffer sb = new StringBuffer(path);
		sb.append(File.separator + classname + ".class");
		return sb.toString();

	}
	
	/**
	 * 根据一个类字节码文件，加载一个类的Class对象到内存
	 * 参数为:.class文件的路径
	 * 加载类的字节码的过程就是读取.class文件的过程
	 */
	public Class<?> findClass2(String name) throws ClassNotFoundException {
		Class<?> cls = null;
		File file = new File(name);
		try {
			InputStream in = new FileInputStream(file);
			//读取这个.class文件的字节码
			byte[] b = new byte[in.available()];//直接声明这个字节大小为这个文件的大小
			int len = in.read(b);//len=621
			System.err.println(len);
			cls = defineClass(b,0,len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cls;
	}
	
	/**
	 * name:cn.itcast.demo.Person
	 * 根据包名找到.class文件
	 * cn.itcast.demo.person = > cn/itcast/demo/Person.class
	 */
	public Class<?> findClass3(String name) throws ClassNotFoundException {
		String classNameWithPackage=name;
		Class<?> cls = null;
		try {
			//先将
			name = name.replace(".","/");
			name +=".class";
			//确定目录
			URL url = MyClassloader.class.getClassLoader().getResource(name);
			System.err.println(">>:"+url.getPath());
			File file = new File(url.getPath());
			InputStream in = new FileInputStream(file);
			//读取这个.class文件的字节码
			byte[] b = new byte[in.available()];//直接声明这个字节大小为这个文件的大小
			int len = in.read(b);//len=621
			System.err.println(len);
			/**
			 * 第一个参数是类名
			 */
			cls = defineClass(classNameWithPackage,b,0,len);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cls;
	}

	/**
	 * @param args
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException {
		MyClassloader my = new MyClassloader("D:\\workspace\\workspace_myeclipse_mine\\Test\\bin");
		Class clazz = my.findClass("Abc");
		clazz.newInstance();
		//Class.forName("Abc");

	}

}
