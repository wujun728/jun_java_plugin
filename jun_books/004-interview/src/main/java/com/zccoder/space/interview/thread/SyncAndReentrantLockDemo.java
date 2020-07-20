package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 题目：多线程之前按顺序调用，实现A->B->C三个线程启动，要求如下
 * <p>AA打印5次，BB打印5次，CC打印5次。紧接着，AA打印5次，BB打印10次，CC打印15次。，一共执行10轮</p>
 *
 * @author zc
 * @date 2020/05/03
 */
public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("SyncAndReentrantLockDemo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 3, 0, TimeUnit.SECONDS, queue, threadFactory);

        MyPrint myPrint = new MyPrint();
        // 共打印10轮
        int count = 10;
        // 线程一
        executor.execute(() -> {
            for (int i = 1; i <= count; i++) {
                myPrint.printA(i);
            }
        });
        // 线程二
        executor.execute(() -> {
            for (int i = 1; i <= count; i++) {
                myPrint.printB(i);
            }
        });
        // 线程三
        executor.execute(() -> {
            for (int i = 1; i <= count; i++) {
                myPrint.printC(i);
            }
        });

        // 关闭线程池
        executor.shutdown();
    }
}
