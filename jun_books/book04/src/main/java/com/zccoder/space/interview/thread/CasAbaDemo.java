package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * ABA问题的解决；使用带时间戳的原子引用类：AtomicStampedReference
 *
 * @author zc
 * @date 2020/05/02
 */
public class CasAbaDemo {

    /**
     * 原子引用，存在ABA问题
     */
    private static AtomicReference<Integer> atomicReference = new AtomicReference<>(100);
    /**
     * 带时间戳的原子引用，创建时需要同时指定初始值和初识时间戳（初识版本号）
     */
    private static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {

        System.out.println("----------------ABA问题的产生----------------");

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("cas-aba-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 线程一
        executor.execute(() -> {
            atomicReference.compareAndSet(100, 101);
            atomicReference.compareAndSet(101, 100);
        });

        // 线程二
        executor.execute(() -> {
            try {
                // 线休眠1秒钟，保证线程一完成了一次ABA操作
                TimeUnit.SECONDS.sleep(1);
                // 修改成功，只要当前内存值为100，则可以修改成功
                boolean b1 = atomicReference.compareAndSet(100, 2020);
                System.out.println("结果：" + b1 + " \t current data：" + atomicReference.get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // main线程休眠3秒，让ABA问题的产生执行完毕后，再执行ABA问题的解决
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("----------------ABA问题的解决----------------");

        // 线程三
        executor.execute(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);

            try {
                // 线休眠1秒钟，让线程四拿到一样的版本号
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 执行一次ABA操作
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第2次版本号：" + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t 第3次版本号：" + atomicStampedReference.getStamp());
        });

        // 线程四
        executor.execute(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t 第1次版本号：" + stamp);

            try {
                // 线休眠3秒钟，保证线程三完成了一次ABA操作
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // 会修改失败，因为版本号已经变了
            boolean result = atomicStampedReference.compareAndSet(100, 2020, stamp, stamp + 1);
            System.out.println(Thread.currentThread().getName() + "\t 结果" + result + "\t 当前最新实际版本号：" + atomicStampedReference.getStamp());
            System.out.println(Thread.currentThread().getName() + "\t 结果" + result + "\t 当前最新实际值：" + atomicStampedReference.getReference());

        });

        // 关闭线程池
        executor.shutdown();
    }

}
