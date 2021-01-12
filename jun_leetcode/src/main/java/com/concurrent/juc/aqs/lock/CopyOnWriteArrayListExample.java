package com.concurrent.juc.aqs.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.Iterator;
import java.util.concurrent.*;

/**
 * ConcurrentHashMap 采用锁分段机制
 * JDK1.8后 采用CAS算法
 *
 * @author BaoZhou
 * @date 2018/7/25
 */
public class CopyOnWriteArrayListExample {
    public static void main(String[] args) {
        //开启线程池（需要引入guava）
        HelloThread thread = new HelloThread();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            pool.execute(thread);
        }
    }
}

class HelloThread implements Runnable {
    /**
     * CopyOnWriteArrayList：每次写入一个元素 底层都会复制出一个新的列表
     * 注意：添加操作多时，效率低
     * 并发迭代操作操作多时可以使用提高效率
     */
    public static CopyOnWriteArrayList<String> list =new CopyOnWriteArrayList();

    static {
        list.add("AA");
        list.add("BB");
        list.add("CC");
    }

    public void run() {
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
            list.add("AA");
        }
    }
}
