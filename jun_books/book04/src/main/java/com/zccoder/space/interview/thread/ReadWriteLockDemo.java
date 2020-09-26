package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 读写锁示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ReentrantLock-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, queue, threadFactory);

        MyCache myCache = new MyCache();

        // 五个写入线程
        int writeTotal = 5;
        for (int i = 1; i <= writeTotal; i++) {
            final int tempInt = i;
            executor.execute(() -> myCache.put(tempInt + "", tempInt + ""));
        }

        // 五个读取线程
        int readTotal = 5;
        for (int i = 1; i <= readTotal; i++) {
            final int tempInt = i;
            executor.execute(() -> myCache.get(tempInt + ""));
        }

        // 关闭线程池
        executor.shutdown();
    }
}
