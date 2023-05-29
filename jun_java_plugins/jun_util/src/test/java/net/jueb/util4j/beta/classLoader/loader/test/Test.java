package net.jueb.util4j.beta.classLoader.loader.test;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import net.jueb.util4j.beta.classLoader.loader.MyUrlClassLoader;

/**
 *@author juebanlin
 *@email juebanlin@gmail.com
 *@createTime 2015年4月12日 下午7:56:35
 **/
public class Test {
	
	public void testResource() throws Exception
	{
		URL url=null;
		//		URL url=new File("C:/Users/Administrator/git/GameProjects/snake/bin").toURI().toURL();
		url=new File("C:/Users/Administrator/Desktop/snake.jar").toURI().toURL();
		
		MyUrlClassLoader cl=new MyUrlClassLoader(new URL[]{url});
		System.out.println(Arrays.toString(cl.getURLs()));
		//第一次加载
		Class<?> clazz=cl.loadClass("net.jueb.game.snake.Start");
		System.out.println(clazz);
		Runnable task=(Runnable)clazz.newInstance();
		//第二次加载
		Class<?> clazz2=cl.loadClass("net.jueb.game.snake.Start");
		System.out.println(clazz2);
		Runnable task2=(Runnable)clazz2.newInstance();
		System.out.println(cl.findResource("META-INF/MANIFEST.MF"));
		//测试类加载和回收
	}
	
	public void testGc() throws MalformedURLException
	{
		//当前工程bin目录下的class进行热加载
		final URL url=Test.class.getClassLoader().getResource(".");
		System.out.println(url);
		TimerTask task=new TimerTask() {
			
			@Override
			public void run() {
				MyUrlClassLoader cl=new MyUrlClassLoader(new URL[]{url});
				try {
					Class<?> clazz=cl.loadClass(TestObj.class.getName());
					Runnable run=(Runnable) clazz.newInstance();
					run.run();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		new Timer().schedule(task, 0,1000);
	}
	public static void main(String[] args) throws Exception {
		new Test().testGc();
	}
	
}
