package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 第三种创建线程的方式
 *
 * @author zc
 * @date 2020/05/03
 */
public class CallableDemo {

    public static void main(String[] args) throws Exception {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("CountDownLatch-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, queue, threadFactory);

        // FutureTask体现的是适配器模式
        FutureTask<Integer> futureTask = new FutureTask<>(new MyCallable());
        // 如果需要重新执行计算任务，则需要新建一个新的对象
        FutureTask<Integer> futureTask2 = new FutureTask<>(new MyCallable());

        executor.execute(futureTask);
        executor.execute(futureTask2);

        System.out.println("main线程****");

        // 要求获得Callable线程的计算结果，如果没有计算完成就要去强求，会导致堵塞，直到计算完成
        Integer result = futureTask.get();
        System.out.println("result：" + result);

        // 关闭线程池
        executor.shutdown();
    }
}
