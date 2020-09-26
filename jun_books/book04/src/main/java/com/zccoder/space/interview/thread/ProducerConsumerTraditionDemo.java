package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 生产者消费者模式传统实现示例
 * <p>题目：一个初始值为零的变量，两个线程对其交替操作，一个加一，一个减一，执行5次</p>
 * <p>1.线程 操作 资源类；2.判断 干活 通知；3.防止虚假唤醒机制</p>
 *
 * @author zc
 * @date 2020/05/03
 */
public class ProducerConsumerTraditionDemo {

    public static void main(String[] args) {

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ProducerConsumerTraditionDemo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, queue, threadFactory);

        ShareData shareData = new ShareData();

        // 执行5次
        int count = 5;

        // 线程一
        executor.execute(() -> {
            for (int i = 0; i < count; i++) {
                shareData.increment();
            }
        });
        // 线程二
        executor.execute(() -> {
            for (int i = 0; i < count; i++) {
                shareData.decrement();
            }
        });

        // 关闭线程池
        executor.shutdown();
    }
}
