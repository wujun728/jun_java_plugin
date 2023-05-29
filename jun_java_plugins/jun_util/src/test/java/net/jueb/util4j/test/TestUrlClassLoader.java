package net.jueb.util4j.test;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.jun.plugin.util4j.file.FileUtil;
import com.jun.plugin.util4j.math.CombinationUtil;

import net.jueb.util4j.beta.classLoader.util.ClassUtil;

/**
 * URLClassLoader 支持jar的url以及包含class文件的目录,匿名类需要添加$才可加载
 * 在Java7的URLClassLoader中提供了释放资源的close方法
 * Java应用，特别是大型的Java应用，往往都需要动态的加载类或Jar，
 * URLClassLoader提供了这个功能，它让我们可以通过以下几种方式进行加载：
 * 文件: (从文件系统目录加载)
 * jar包: (从Jar包进行加载)
 * Http: (从远程的Http服务进行加载)
 * 常见的问题是，当class文件或者resources资源文件更新后，我们需要重新加载这些类或者Jar。
 * 从理论上来说，当应用清理了对所加载的对象的引用，那么垃圾收集器就会将这些对象给收集掉，
 * 然后我们再重新加载新的JAR文件，并创建一个新的URLClassLoader来加载。
 * 可是这里有一个问题，就是我们不知道垃圾收集器什么时候将那些未被引用的对象给收集掉，
 * 特别是在Windows中，因为在Windows中打开的文件是不可以被删除或被替换的。
 * 官方说明:https://blogs.oracle.com/CoreJavaTechTips/entry/closing_a_urlclassloader
 * 1.已经load的class,删除文件后,仍然存在loader中
 * 2.URLClassLoader的url只支持目录的class搜寻,如果目录下面有jar,则搜索不到jar的class
 * @author Administrator
 */
public class TestUrlClassLoader {

	
	/** 
     * 获取所有文件列表 
     * @param rootFile 
     * @param fileList 
     * @throws IOException 
     */  
    public static List<String> listFiles(File rootFile,List<String> fileList) throws IOException{  
        File[] allFiles = rootFile.listFiles();  
        for(File file : allFiles){  
            if(file.isDirectory()){  
                listFiles(file, fileList);  
            }else{  
                String path = file.getCanonicalPath(); 
                String apath=file.getAbsolutePath();
                String clazz = path.substring(path.indexOf("classes")+8);  
                fileList.add(clazz.replace("//", ".").substring(0,clazz.lastIndexOf(".")));  
            }  
        }  
        return fileList;  
    } 
    
    public static void testFiles() throws IOException
    {
    	File f=new File("C:/Users/Administrator/git/util4j/util4j/target/123");
    	List<String> fileList=new ArrayList<>();
    	listFiles(f, fileList);
    	System.out.println(fileList);
    }
    
    /**
     * 测试资源释放
     * @throws IOException
     */
    public static void testRelase() throws Exception
    {
    	File f=new File("C:/Users/Administrator/git/util4j/util4j/target/util4j-3.7.6_beta.jar");
    	URL url=f.toURI().toURL();
    	JarFile jf=new JarFile(f);//文件被占用,不可删除修改
    	Map<String, JarEntry> classs=FileUtil.findClassByJar(jf);
    	System.out.println(classs.size()+":"+classs.toString());
    	jf.close();//释放文件占用
		URL[] urls=new URL[]{url};
		URLClassLoader loader=new URLClassLoader(urls);
		Class c1=loader.loadClass("net.jueb.util4j.math.CombinationUtil");
		System.out.println(c1);
		loader.close();
    }
	
	public static void testDir()throws Exception
	{
		File f=new File("C:/Users/Administrator/git/util4j/util4j/target/classes");
		URL url=f.toURI().toURL();
		URL[] urls=new URL[]{url};
		URLClassLoader loader=new URLClassLoader(urls);
//		loader.close();
		Class c1=loader.loadClass("net.jueb.util4j.math.CombinationUtil");
		System.out.println(c1);
		Class c2=loader.loadClass("net.jueb.util4j.math.CombinationUtil$CombinationController");
		System.out.println(c2);
		Class c3=loader.loadClass("net.jueb.util4j.math.CombinationUtil$ForEachByteIndexController");
		System.out.println(c3);
		Enumeration<URL> ss=loader.findResources("*.class");
		System.out.println(ss.hasMoreElements());
		CombinationUtil c22=(CombinationUtil) c1.newInstance();
		System.out.println(c22);
		loader.close();
		c1=loader.loadClass("net.jueb.util4j.math.CombinationUtil");
		System.out.println(c1);
	}
	
	/**
	 * dir包含jar和子目录的class
	 * @throws Exception
	 */
	public static void testDir2()throws Exception
	{
		String dir="C:/Users/jaci/git/util4j/util4j/target/classes";
		File f=new File(dir);
		URL url=f.toURI().toURL();
		URL[] urls=new URL[]{url};
		URLClassLoader loader=new URLClassLoader(urls);
//		loader.close();
		Class dirClass=loader.loadClass("net.jueb.util4j.math.CombinationUtil");//寻找到
		System.out.println(dirClass);
		Class JarClass=loader.loadClass("net.xzmj.core.common.ServerInfo");//不能寻找到
		System.out.println(JarClass);
		
	}
	
	public static void main(String[] args) throws Exception {
		testDir2();
	}
}
