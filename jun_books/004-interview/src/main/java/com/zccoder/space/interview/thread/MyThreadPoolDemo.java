package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(3);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("MyThreadPoolDemo-pool-%d").build();
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        ExecutorService executor = new ThreadPoolExecutor(2, 5, 1, TimeUnit.SECONDS, queue, threadFactory, handler);

        try {
            // 模拟10个用户办理业务，每个用户就是一个来自外部的请求线程
            int total = 10;
            for (int i = 1; i <= total; i++) {
                executor.execute(() -> System.out.println(Thread.currentThread().getName() + "\t 办理业务"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }
}
