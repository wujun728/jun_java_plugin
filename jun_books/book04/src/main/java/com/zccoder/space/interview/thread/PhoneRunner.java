package com.zccoder.space.interview.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 线程操作资源类
 *
 * @author zc
 * @date 2020/05/03
 */
public class PhoneRunner implements Runnable {

    private Lock lock = new ReentrantLock();

    @Override
    public void run() {
        this.doRun();
    }

    private void doRun() {
        // 加锁几次，解锁也需要与之对应，两两配对。如果解锁次数少于解锁次数，会造成死锁
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked doRun()");
            this.doRunItem();
        } finally {
            lock.unlock();
        }
    }

    private void doRunItem() {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t invoked doRunItem()");
        } finally {
            lock.unlock();
        }
    }
}