package com.concurrent.juc.aqs;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * 闭锁：在完成某些运算时，只有其他所有的运算全部完成，当前的运算才会继续执行
 * 相当于计数器
 *
 * @author BaoZhou
 * @date 2018/7/26
 */
@Slf4j
public class CountDownLatchExample {
    public static void main(String[] args) {
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();

        //Common Thread Pool
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        CountDownLatch latch = new CountDownLatch(10);
        long start = System.currentTimeMillis();
        for (int i = 0; i < 10; i++) {
            final int ThreadNum = i;
            pool.execute(() -> {
                try {
                    log.info("{} is ready", ThreadNum);
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                latch.countDown();
            });

        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pool.shutdown();
        long end = System.currentTimeMillis();
        System.out.println("总共用时:" + (end - start));

    }
}
