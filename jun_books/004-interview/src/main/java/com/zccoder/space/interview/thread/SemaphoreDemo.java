package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Semaphore示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("SemaphoreDemo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 模拟3个停车位
        int permits = 3;
        Semaphore semaphore = new Semaphore(permits);

        // 模拟6辆汽车
        int carTotal = 6;
        for (int i = 1; i <= carTotal; i++) {
            executor.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    // 假设每辆车停3秒钟
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 停车3秒后离开车位");
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                } finally {
                    semaphore.release();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();
    }
}
