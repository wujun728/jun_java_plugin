package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 生产者消费者模式阻塞队列实现示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class ProducerConsumerBlockQueueDemo {

    public static void main(String[] args) throws Exception {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ProducerConsumerTraditionDemo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 2, 0, TimeUnit.SECONDS, queue, threadFactory);

        MyResource myResource = new MyResource(new ArrayBlockingQueue<>(10));

        // 生产线程
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 生产线程启动");
            try {
                myResource.myProducer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 消费线程
        executor.execute(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 消费线程启动");
            try {
                myResource.myConsumer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // main线程休眠5秒
        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到，大老板main线程叫停，活动结束");
        myResource.stop();

        // 关闭线程池
        executor.shutdown();
    }
}
