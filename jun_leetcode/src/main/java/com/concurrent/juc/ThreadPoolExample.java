package com.concurrent.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 线程池调度  ScheduledExecutorService
 * @author BaoZhou
 * @date 2018/7/29
 */
public class ThreadPoolExample {
    public static void main(String[] args) {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
        Future<Integer> result = pool.schedule(() -> {
            int num = new Random().nextInt(100);
            System.out.println(Thread.currentThread().getName() + num);
            return num;
        }, 2, TimeUnit.SECONDS);
        try {
            System.out.println(result.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    pool.shutdown();
    }
}
