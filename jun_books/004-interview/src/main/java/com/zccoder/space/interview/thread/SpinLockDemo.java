package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class SpinLockDemo {

    /**
     * 创建原子引用线程
     */
    private AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("SpinLockDemo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, queue, threadFactory);

        SpinLockDemo spinLockDemo = new SpinLockDemo();

        // 线程一
        executor.execute(() -> {
            spinLockDemo.myLock();
            // 线程休眠5秒
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.myUnLock();
        });

        // main线程休眠1秒，让线程一成功占用锁
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 线程二
        executor.execute(() -> {
            spinLockDemo.myLock();
            spinLockDemo.myUnLock();
        });

        // 关闭线程池
        executor.shutdown();
    }

    private void myLock() {
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t come in");

        while (true) {
            if (atomicReference.compareAndSet(null, thread)) {
                break;
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t invoked myLock()");
    }

    private void myUnLock() {
        Thread thread = Thread.currentThread();
        atomicReference.compareAndSet(thread, null);
        System.out.println(Thread.currentThread().getName() + "\t invoked myUnLock()");
    }
}
