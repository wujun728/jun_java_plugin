package com.concurrent.datatime;


import com.concurrent.juc.annotation.ThreadSafe;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.concurrent.*;

/**
 * JodaTime线程安全
 * @author BaoZhou
 * @date 2019/1/6
 */
@Slf4j
@ThreadSafe
public class JodaTimeDemo {
    public static int clientTotal = 1000;

    public static int threadTotal = 200;

    private static DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyyMMdd");

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
                update(count);
                semaphore.release();
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        pool.shutdown();
    }

    private static void update(int count) {
        Date date = DateTime.parse("20190606", dateTimeFormatter).toDate();
        log.info("{},{}",count,date.toString());
    }

}
