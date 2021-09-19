package com.jun.plugin.servlet.guice.user.action;

public class Test {

	public static void main(String[] args) {
		//得到程序运行时的当前线程
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread);
        //ThreadLocal一个容器，向这个容器存储的对象，在当前线程范围内都可以取得出来
        ThreadLocal<String> t = new ThreadLocal<String>();
        //把某个对象绑定到当前线程上 对象以键值对的形式存储到一个Map集合中，对象的的key是当前的线程，如： map(currentThread,"aaa")
        t.set("aaa");
        //获取绑定到当前线程中的对象
        String value = t.get();
        //输出value的值是aaa
        System.out.println(value);
	}
}
