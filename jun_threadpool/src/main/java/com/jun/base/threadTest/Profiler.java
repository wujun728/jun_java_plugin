package com.jun.base.threadTest;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 *
 Profiler可以被复用在方法调用耗时统计的功能上，在方法的入口前执行begin()方法，在
 方法调用后执行end()方法，好处是两个方法的调用不用在一个方法或者类中，比如在AOP（面
 向方面编程）中，可以在方法调用前的切入点执行begin()方法，而在方法调用后的切入点执行
 end()方法，这样依旧可以获得方法的执行耗时。


 ThreadLocal使用
 http://ifeve.com/threadlocal%E4%BD%BF%E7%94%A8/
 *
 */
public class Profiler {
    // 第一次get()方法调用时会进行初始化（如果set方法没有调用），每个线程会调用一次
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };
    public static final void begin() {
        TIME_THREADLOCAL.set(System.currentTimeMillis());
    }
    public static final long end() {
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
    public static void main(String[] args) throws Exception {
        Profiler.begin();
        TimeUnit.SECONDS.sleep(1);
        System.out.println("Cost: " + Profiler.end() + " mills");

    }
}
