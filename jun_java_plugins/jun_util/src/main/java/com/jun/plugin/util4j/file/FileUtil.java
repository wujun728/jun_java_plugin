package com.jun.plugin.util4j.file;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.lang.NullArgumentException;

public class FileUtil {
	
	/**
	 * 创建系统临时目录并在jvm关闭后自动删除
	 * @param folderName
	 * @return
	 */
	public static File createTmpDir(String folderName)
	{
		File temp = new File(System.getProperty("java.io.tmpdir"), folderName);
		if (!temp.exists()) {
			temp.mkdirs();
			temp.deleteOnExit();
		}
		return temp;
	}
	
	public static void copyTo(File file,File target) throws IOException
	{
		Files.copy(file.toPath(), target.toPath(), StandardCopyOption.COPY_ATTRIBUTES,StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void copyTo(File file,String dir) throws IOException
	{
		File target=new File(dir,file.getName());
		copyTo(file, target);
	}
	
	/**
	 * 寻找目录和子目录下面指定后缀的文件
	 * @param dir 根目录
	 * @param sub 是否搜索子目录
	 * @return
	 */
	public static final Set<File> findFileByDirAndSub(File dir,String suffix)
	{
		Set<File> files=new HashSet<File>();
		if(dir==null)
		{
			throw new NullArgumentException("dir ==null");
		}
		if(dir.isFile())
		{
			throw new IllegalArgumentException("dir "+dir+" is not a dir");
		}
		if(!dir.exists())
		{
			throw new IllegalArgumentException("dir "+dir+" not found");
		}
		Stack<File> dirs = new Stack<File>();
		dirs.push(dir);
		while (!dirs.isEmpty()) 
		{
			File path = dirs.pop();
			File[] fs = path.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) 
				{
					return pathname.isDirectory() || pathname.getName().endsWith(suffix);
				}
			});
			for (File subFile : fs) 
			{
				if (subFile.isDirectory()) 
				{
					dirs.push(subFile);
				} else 
				{
					files.add(subFile);
				}
			}
		}
		return files;
	}
	
	/**
	 * 以一个class包根目录获取所有的class文件
	 * 如果该目录为c:/bin 且目录结构为c:/bin/net/jueb/util/A.class 则有net.jueb.util.A这个类
	 * @param classFolder
	 * @return
	 */
	public static final HashMap<String,File> findClassByDirAndSub(File dir)
	{
		if(dir==null)
		{
			throw new NullArgumentException("dir ==null");
		}
		if(dir.isFile())
		{
			throw new IllegalArgumentException("dir "+dir+" is not a dir");
		}
		if(!dir.exists())
		{
			throw new IllegalArgumentException("dir "+dir+" not found");
		}
		HashMap<String,File> clazzs=new HashMap<String,File>();
		File clazzPath = dir;
		String Suffix=".class";
		if (clazzPath.exists() && clazzPath.isDirectory()) 
		{
			// 获取路径长度
			int clazzPathLen = clazzPath.getAbsolutePath().length() + 1;
			Stack<File> stack = new Stack<>();
			stack.push(clazzPath);
			// 遍历类路径
			while (stack.isEmpty() == false) 
			{
				File path = stack.pop();
				File[] classFiles = path.listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return pathname.isDirectory() || pathname.getName().endsWith(Suffix);
					}
				});
				for (File subFile : classFiles) 
				{
					if (subFile.isDirectory()) 
					{
						stack.push(subFile);
					} else 
					{
						String className = subFile.getAbsolutePath();
						className = className.substring(clazzPathLen, className.length() - Suffix.length());
						className = className.replace(File.separatorChar, '.');
						clazzs.put(className, subFile);
					}
				}
			}
		}
		return clazzs;
	}
	
	/**
	 * 搜索jar里面的class
	 * 注意jar的open和close
	 * 返回类名和类的map集合
	 * @throws IOException 
	 * */
	public static final Map<String,JarEntry> findClassByJar(JarFile jarFile) throws IOException {
		Map<String,JarEntry> map=new HashMap<String,JarEntry>();
		if(jarFile==null)
		{
			throw new RuntimeException("jarFile is Null");
		}
		String Suffix=".class";
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) 
		{//遍历jar的实体对象
			JarEntry jarEntry = jarEntries.nextElement();
			if(jarEntry.isDirectory() || !jarEntry.getName().endsWith(Suffix))
			{
				continue;
			}
			String jarEntryName = jarEntry.getName(); // 类似：sun/security/internal/interfaces/TlsMasterSecret.class
			String className = jarEntryName.substring(0, jarEntryName.length() - Suffix.length());//sun/security/internal/interfaces/TlsMasterSecret
			//注意,jar文件里面的只能是/不能是\
			className = className.replace("/", ".");//sun.security.internal.interfaces.TlsMasterSecret
			map.put(className,jarEntry);
		}
		return map;
	}

	/**
	 * 寻找目录下面的jar
	 * @param dir
	 * @return
	 */
	public static final File[] findJarFileByDir(File dir)
	{
		if(dir==null)
		{
			throw new NullArgumentException("dir ==null");
		}
		if(dir.isFile())
		{
			throw new IllegalArgumentException("dir "+dir+" is not a dir");
		}
		if(!dir.exists())
		{
			throw new IllegalArgumentException("dir "+dir+" not found");
		}
		File[] jarFiles = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) 
				{
					return pathname.getName().endsWith(".jar");
				}
			});
		return jarFiles;
	}
	
	/**
	 * 寻找目录和子目录下面的jar
	 * @param dir 根目录
	 * @param sub 是否搜索子目录
	 * @return
	 */
	public static final Set<File> findJarFileByDirAndSub(File dir)
	{
		Set<File> files=new HashSet<File>();
		if(dir==null)
		{
			throw new NullArgumentException("dir ==null");
		}
		if(dir.isFile())
		{
			throw new IllegalArgumentException("dir "+dir+" is not a dir");
		}
		if(!dir.exists())
		{
			throw new IllegalArgumentException("dir "+dir+" not found");
		}
		Stack<File> dirs = new Stack<File>();
		dirs.push(dir);
		while (!dirs.isEmpty()) 
		{
			File path = dirs.pop();
			File[] fs = path.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) 
				{
					return pathname.isDirectory() || pathname.getName().endsWith(".jar");
				}
			});
			for (File subFile : fs) 
			{
				if (subFile.isDirectory()) 
				{
					dirs.push(subFile);
				} else 
				{
					files.add(subFile);
				}
			}
		}
		return files;
	}
	
	/**
	 * 返回jar文件所有的目录和文件资源
	 * 注意jar的open和close
	 * @param jarFile
	 * @return
	 */
	public static final List<JarEntry> findJarEntrysByJar(JarFile jarFile)
	{
		List<JarEntry> list=new ArrayList<JarEntry>();
		if(jarFile==null)
		{
			throw new RuntimeException("jarFile is Null");
		}
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		while (jarEntries.hasMoreElements()) 
		{//遍历jar的实体对象
			JarEntry jarEntry = jarEntries.nextElement();
			list.add(jarEntry);
		}
		return list;
	}
}
