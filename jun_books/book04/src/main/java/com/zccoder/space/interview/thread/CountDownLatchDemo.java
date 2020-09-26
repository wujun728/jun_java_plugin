package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * CountDownLatch示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class CountDownLatchDemo {

    public static void main(String[] args) throws Exception {
        closeDoor();

        System.out.println("---------------------");

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("CountDownLatch-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(8, 8, 0, TimeUnit.SECONDS, queue, threadFactory);

        int total = 6;
        CountDownLatch countDownLatch = new CountDownLatch(total);

        for (int i = 1; i <= total; i++) {
            final int tempInt = i;
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t" + CountryEnum.getByCode(tempInt).getName() + "国，被灭");
                // 每灭一国，计数器减一
                countDownLatch.countDown();
            });
        }

        // main线程等待计数器为0时，才能继续执行
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t 秦帝国，一统华夏");

        System.out.println();
        System.out.println(CountryEnum.ONE);
        System.out.println(CountryEnum.ONE.getCode());
        System.out.println(CountryEnum.ONE.getName());

        // 关闭线程池
        executor.shutdown();
    }

    /**
     * 教室关门的例子
     */
    private static void closeDoor() throws InterruptedException {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("CountDownLatch-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 0, TimeUnit.SECONDS, queue, threadFactory);

        int total = 6;
        CountDownLatch countDownLatch = new CountDownLatch(total);

        for (int i = 1; i <= total; i++) {
            executor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t 上完自习，离开教室");
                // 每走一个人，计数器减一
                countDownLatch.countDown();
            });
        }

        // main线程等待计数器为0时，才能继续执行
        countDownLatch.await();

        System.out.println(Thread.currentThread().getName() + "\t 班长最后关门走人");

        // 关闭线程池
        executor.shutdown();
    }
}
