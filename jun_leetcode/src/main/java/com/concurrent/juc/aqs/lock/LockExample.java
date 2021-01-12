package com.concurrent.juc.aqs.lock;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 解决多线程安全问题
 * 隐式锁
 * 1.同步代码块
 * 2.同步方法
 * jdk 1.5后
 * <p>
 * 显示锁
 * 3.同步锁
 *
 * @author BaoZhou
 * @date 2018/7/27
 */
public class LockExample {
    public static void main(String[] args) {
//        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
//        ExecutorService pool = new ThreadPoolExecutor(1, 10,
//                0L, TimeUnit.MILLISECONDS,
//                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        Ticket ticket = new Ticket();
        new Thread(ticket, "1").start();
        new Thread(ticket, "2").start();
        new Thread(ticket, "3").start();
        new Thread(ticket, "4").start();

//
//        pool.execute(ticket);
//        pool.execute(ticket);
//        pool.execute(ticket);
//        pool.execute(ticket);
//        pool.shutdown();
    }
}

class Ticket implements Runnable {
    private int tick = 1000;
    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        while (true) {
            try {
                if (tick > 0) {
                    Thread.sleep(200);
                    lock.lock();
                    System.out.println(Thread.currentThread().getName() + "完成售票，余票：" + --tick);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}