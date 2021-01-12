package com.concurrent.juc.aqs.stampedlock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;
import java.util.concurrent.locks.StampedLock;

/**
 * StampLock
 * @author BaoZhou
 * @date 2019/1/8
 */
@Slf4j
public class StampLockSample {
    public static int clientTotal = 10000;

    public static int threadTotal = 200;

    public static int count = 0;

    private final static StampedLock lock = new StampedLock();

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService pool = new ScheduledThreadPoolExecutor(20,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            pool.execute(() ->
            {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                add();
                semaphore.release();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        pool.shutdown();
        log.info("{}",count);
    }

    private static void add() {
        long stamp = lock.writeLock();
        try{
            count++;
        }
        finally {
            lock.unlock(stamp);
        }
    }
}
