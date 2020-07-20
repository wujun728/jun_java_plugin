package com.zccoder.space.interview.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 资源类
 *
 * @author zc
 * @date 2020/05/03
 */
class MyResource {

    /**
     * 默认开启，进行生产和消费
     */
    private volatile boolean flag = true;

    private AtomicInteger atomicInteger = new AtomicInteger();

    private BlockingQueue<String> blockingQueue;

    MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    void myProducer() throws Exception {
        String data;
        boolean result;

        while (flag) {
            data = atomicInteger.incrementAndGet() + "";
            result = blockingQueue.offer(data, 2L, TimeUnit.SECONDS);
            if (result) {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列 " + data + " 成功");
            } else {
                System.out.println(Thread.currentThread().getName() + "\t 插入队列 " + data + " 失败");
            }
            TimeUnit.SECONDS.sleep(1);
        }

        System.out.println(Thread.currentThread().getName() + "\t 大老板叫停了，表示flag=false，生产动作结束");
    }

    void myConsumer() throws Exception {
        String result;
        while (flag) {
            result = blockingQueue.poll(2L, TimeUnit.SECONDS);
            if (null == result || "".equalsIgnoreCase(result)) {
                flag = false;
                System.out.println(Thread.currentThread().getName() + "\t 超过2秒钟没有取到蛋糕，消费退出");
                return;
            }
            System.out.println(Thread.currentThread().getName() + "\t 消费队列蛋糕 " + result + " 成功");
        }
    }

    void stop() {
        this.flag = false;
    }
}
