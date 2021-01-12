package com.concurrent.juc.aqs.lock;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1.ReadWriteLockExample:读写锁
 * <p>
 * 写写/读写 需要互斥
 * 读读 不需要互斥
 *
 * @author BaoZhou
 * @date 2018/7/29
 */
public class ReadWriteLockExample {
    private static final int READ_THREAD_NUM = 500;
    private static final int WRITE_THREAD_NUM = 1;

    public static void main(String[] args) {

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(500, 2000,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        ReadThread readThread = new ReadThread(readWriteLockDemo);
        WriteThread writeThread = new WriteThread(readWriteLockDemo);
        for (int i = 0; i < READ_THREAD_NUM; i++) {
            pool.execute(readThread);
        }

        for (int i = 0; i < WRITE_THREAD_NUM; i++) {
            pool.execute(writeThread);
        }
        pool.shutdown();
    }
}

class ReadThread implements Runnable {
    ReadWriteLockDemo demo;

    public ReadThread(ReadWriteLockDemo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        demo.get();
    }
}

class WriteThread implements Runnable {
    ReadWriteLockDemo demo;

    public WriteThread(ReadWriteLockDemo demo) {
        this.demo = demo;
    }

    @Override
    public void run() {
        demo.set();
    }
}

class ReadWriteLockDemo {
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private int num;

    void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t" + "READ:" + num);
        } finally {
            lock.readLock().unlock();
        }
    }

    void set() {
        lock.writeLock().lock();
        try {
//                Random r1 = new Random(100);
            num = 6;
            System.out.println("Write:" + num);
        } finally {
            lock.writeLock().unlock();
        }
    }
}