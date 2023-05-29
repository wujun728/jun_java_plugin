package net.jueb.util4j.test;

import java.util.Timer;
import java.util.TimerTask;

import net.jueb.util4j.test.classLoader.MyUrlClassLoader;

public class Test {

	public static void main(String[] args) {
		final MyUrlClassLoader cl=new MyUrlClassLoader();//用HotSwapClassLoder定义类
		Thread.currentThread().setContextClassLoader(cl);
		Timer t=new Timer();
		TimerTask tt=new TimerTask() {
			@Override
			public void run() {
				try {
					System.out.println(Thread.currentThread().getContextClassLoader().getClass().getName());
					String name="test.H1";
					System.out.println("开始加载对象："+name);
					//获取bin目录
					String dir=Test.class.getClassLoader().getResource(".").getFile();
					System.out.println(dir);
					Class<?> c=cl.loadClass(name);//初始类
					System.out.println("对象由"+c.getClassLoader().toString()+"加载");
					HotSwap h1=(HotSwap)c.newInstance();//转换为接口类型(缺点:不能调用子类方法)，方便接口调用方法，否则只能使用反射调用,
					if(h1!=null)
					{
						h1.show();
					}else
					{
						System.out.println("加载对象为null");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		t.schedule(tt, 0, 10000);
	}
}
