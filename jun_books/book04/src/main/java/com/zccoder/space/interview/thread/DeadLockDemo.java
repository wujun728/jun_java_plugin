package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 死锁示例
 *
 * @author zc
 * @date 2020/05/04
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(3);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("DeadLockDemo-pool-%d").build();
        ExecutorService executor = new ThreadPoolExecutor(2, 2, 1, TimeUnit.SECONDS, queue, threadFactory);

        String lockA = "lockA";
        String lockB = "lockB";

        executor.execute(new DeadLockHold(lockA, lockB));
        executor.execute(new DeadLockHold(lockB, lockA));

        // 关闭线程池
        executor.shutdown();
    }
}
