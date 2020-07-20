package com.zccoder.space.interview.oom;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * java.lang.OutOfMemoryError: unable to create new native thread 示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class UnableCreateNewThreadDemo {
    public static void main(String[] args) {

        // 创建线程工厂
        AtomicInteger nextId = new AtomicInteger(1);
        ThreadFactory threadFactory = (task) -> {
            String name = "UnableCreateNewThreadDemo-thread-" + nextId.getAndIncrement();
            return new Thread(null, task, name, 0);
        };
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1);
        ThreadPoolExecutor executor = new ThreadPoolExecutor(Integer.MAX_VALUE, Integer.MAX_VALUE, 0, TimeUnit.SECONDS, queue, threadFactory);

        for (int i = 1; ; i++) {
            System.out.println(" i = " + i);
            executor.execute(() -> {
                try {
                    Thread.sleep(Integer.MAX_VALUE);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}