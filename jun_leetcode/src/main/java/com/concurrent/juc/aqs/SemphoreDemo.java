package com.concurrent.juc.aqs;


import com.concurrent.juc.annotation.ThreadSafe;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * semaphore.tryAcquire 拿不到就丢弃
 * @author BaoZhou
 * @date 2019/1/6
 */
@Slf4j
@ThreadSafe
public class SemphoreDemo {
    public static int clientTotal = 10000;

    public static int threadTotal = 4;

    private static List<Integer> list = Collections.synchronizedList(Lists.newArrayList());

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            pool.execute(() ->
            {
                if (semaphore.tryAcquire(3)) {
                    update(count);
                    semaphore.release(3);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        pool.shutdown();
        log.info("{}", list.size());
    }

    private static void update(int count) {
        list.add(count);
    }
}
