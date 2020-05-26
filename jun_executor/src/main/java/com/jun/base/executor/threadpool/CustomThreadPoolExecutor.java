package com.jun.base.executor.threadpool;

import java.util.concurrent.*;

/**
 * 描述: 自定义线程池
 *
 * @author : lhb
 * @date : 2020-02-01 19:14
 */
public class CustomThreadPoolExecutor {
    private ThreadPoolExecutor pool = null;

    /**
     * 线程池初始化方法
     * corePoolSize 核心线程池大小----10
     * maximumPoolSize 最大线程池大小----30
     * keepAliveTime 线程池中超过corePoolSize数目的空闲线程最大存活时间----30+单位TimeUnit
     * TimeUnit keepAliveTime时间单位----TimeUnit.MINUTES
     * workQueue 阻塞队列----new ArrayBlockingQueue<Runnable>(10)====10容量的阻塞队列
     * threadFactory 新建线程工厂----new CustomThreadFactory()====定制的线程工厂
     * rejectedExecutionHandler 当提交任务数超过maxmumPoolSize+workQueue之和时,
     * 任务会交给RejectedExecutionHandler来处理
     */
    public void init() {

        pool = new ThreadPoolExecutor(10, 30, 30,
                TimeUnit.MINUTES, new ArrayBlockingQueue<>(10), new CustomThreadFactory(), new CustomRejectedExecutionHandler());
    }

    /**
     * 线程池销毁方法
     */
    public void destory() {
        if (pool != null) {
            pool.shutdown();
        }
    }

    /**
     * 获取线程池的实例
     * @return
     */
    public ExecutorService getCustomThreadPoolExecutor() {
        return this.pool;
    }


    /**
     * 当提交任务数超过maxmumPoolSize+workQueue之和时，任务会交给RejectedExecutionHandler来处理
     */
    private class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            //记录异常
//            System.out.println("error...................");
            //将指定元素插入此队列中，将等待可用的空间.通俗点说就是>maxSize 时候，阻塞，直到能够有空间插入元素
            try {
                executor.getQueue().put(r);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 实现线程工厂方法
     */
    private class CustomThreadFactory implements ThreadFactory {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    }

    public static void main(String[] args) {
        CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();

        //1. 初始化
        exec.init();

        ExecutorService executorService = exec.getCustomThreadPoolExecutor();

        for (int i = 0; i < 100; i++) {
            System.out.println("提交第" + i + "个任务");
            executorService.execute(() -> {
                try {
                    System.out.println("执行自定义的线程池:" + Thread.currentThread().getName());
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        try {
            //销毁
            Thread.sleep(1000);
            exec.destory();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
