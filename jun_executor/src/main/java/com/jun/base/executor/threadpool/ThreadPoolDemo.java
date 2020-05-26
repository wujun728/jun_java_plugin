package com.jun.base.executor.threadpool;

import java.util.concurrent.*;

/**
 * 描述: JAVA线程池实现
 *
 * @author : lhb
 * @date : 2020-2-1 14:34
 */
public class ThreadPoolDemo {

    /**
     * 启动固定线程数的线程池
     *
     * @param size 线程池中最大可创建多少个线程
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    public void fixedThreadPool(int size) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(size);
        for (int i = 0; i < 10; i++) {
            Future<Integer> task = executorService.submit(() -> {
                System.out.println("执行线程" + Thread.currentThread().getName());
                return 40;
            });
            System.out.println("第" + i + "次计算,结果:" + task.get());
        }

        //销毁线程池
        executorService.shutdown();
    }

    /**
     * 按需分配的线程池
     * CachedThreadPool比fixedThreadPool更快
     * 只要有任务并且其他线程都在活跃态，就会开启一个新的线程
     * 最大线程数：Integer.MAX_VALUE = 0x7fffffff
     */
    public void cacheThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            Future<Integer> task = executorService.submit(() -> {
                System.out.println("执行线程" + Thread.currentThread().getName());
                return 50;
            });
            System.out.println("第" + i + "次计算,结果:" + task.get());
        }

        //销毁线程池
        executorService.shutdown();
    }

    /**
     * 单线程化的线程池
     * 串行执行所有任务
     */
    public void singleThreadPool() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            Future<Integer> task = executorService.submit(() -> {
                Thread.currentThread().setName("Thread i = " + index);
                System.out.println("执行单线程化的线程池:" + Thread.currentThread().getName());
                return 50;
            });
            System.out.println("第" + i + "次计算,结果:" + task.get());
        }

        //销毁线程池
        executorService.shutdown();
    }

    /**
     * 定时定期的线程池
     */
    public void scheduledThreadPool() {
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(10);

        //延迟3s后运行
        for (int i = 0; i < 10; i++) {
            scheduledThreadPool.schedule(() ->
                    System.out.println("执行线程" + Thread.currentThread().getName()), 10, TimeUnit.SECONDS);
        }

//        scheduledThreadPool.scheduleAtFixedRate(() ->
//                System.out.println("scheduleAtFixedRate：执行线程" + Thread.currentThread().getName()), 1,1, TimeUnit.SECONDS);

        //销毁线程池
        scheduledThreadPool.shutdown();
    }
}
