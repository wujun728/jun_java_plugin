package com.concurrent.juc.aqs;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import java.util.concurrent.*;

/**
 * @author BaoZhou
 * @date 2019/1/7
 */
@Slf4j
public class CyclicBarrierDemo {
    static CyclicBarrier cyclicBarrier = new CyclicBarrier(5, () -> log.info("callback is running"));

    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(20,
                new BasicThreadFactory.Builder().namingPattern("example-schedule-pool-%d").daemon(true).build());
        for (int i = 0; i < 20; i++) {
            final int ThreadNum = i;
            Thread.sleep(1000);
            executorService.execute(() ->
            {
                try {
                    race(ThreadNum);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }

    private static void race(int num) throws InterruptedException, BrokenBarrierException, TimeoutException {
        Thread.sleep(1000);
        log.info("{} is ready", num);
        cyclicBarrier.await();
        //cyclicBarrier.await(1000, TimeUnit.MILLISECONDS);
        log.info("{} is continue", num);
    }
}
