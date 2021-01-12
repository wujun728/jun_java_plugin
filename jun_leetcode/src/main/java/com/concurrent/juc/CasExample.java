package com.concurrent.juc;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 模拟CAS算法
 *
 * @author BaoZhou
 * @date 2018/7/25
 */
public class CasExample {
    public static void main(String[] args) {
        //开启线程池（需要引入guava）
        CasThread casThread = new CasThread();
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 10; i++) {
            pool.execute(casThread);
        }
    }
}


class CasThread implements Runnable {
    final Cas cas = new Cas();
    public void run() {
        try {
            int exceptedValue = cas.get();
            System.out.println(cas.compareAndSet(exceptedValue, (int) (Math.random() * 100)));
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Cas {
    private int value;

    public Cas() {
    }

    public synchronized int get() {
        return value;
    }

    public synchronized int compareAndSwap(int exceptValue, int newValue) {
        int oldValue = value;
        if (oldValue == exceptValue) {
            this.value = newValue;
        }
        return oldValue;
    }

    public synchronized boolean compareAndSet(int exceptValue, int newValue) {
        return exceptValue == compareAndSwap(exceptValue, newValue);
    }
}

