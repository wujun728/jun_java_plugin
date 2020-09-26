package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CyclicBarrier示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class CyclicBarrierDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("CyclicBarrierDemo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 七颗龙珠
        int total = 7;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(total, () -> {
            // 编写七个线程都await后，需要执行的代码
            System.out.println("召唤神龙");
        });

        for (int i = 1; i <= total; i++) {
            final int tempInt = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 收集到第：" + tempInt + "颗龙珠");
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        // 关闭线程池
        executor.shutdown();
    }
}
