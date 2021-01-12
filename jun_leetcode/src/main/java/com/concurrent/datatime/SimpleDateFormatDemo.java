package com.concurrent.datatime;


import com.concurrent.juc.annotation.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * SimpleDateFormat线程不安全，需要使用堆栈封闭规则才能保证线程安全
 * @author BaoZhou
 * @date 2019/1/6
 */
@Slf4j
@NotThreadSafe
public class SimpleDateFormatDemo {
    public static int clientTotal = 1000;

    public static int threadTotal = 200;

    private static SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("yyyyMMdd");

    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            pool.execute(() ->
            {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    update2(count);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                semaphore.release();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        pool.shutdown();
    }

    private static void update(int count) throws ParseException {
        dateTimeFormatter.parse("20190106");
    }
    private static void update2(int count) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        format.parse("20190106");
    }
}
