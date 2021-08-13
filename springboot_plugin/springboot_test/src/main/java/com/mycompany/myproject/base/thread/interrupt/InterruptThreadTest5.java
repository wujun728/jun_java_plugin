package com.mycompany.myproject.base.thread.interrupt;


//不可中断线程
//有一种情况是线程不能被中断的，就是调用synchronized关键字和reentrantLock.lock()获取锁的过程。
//但是如果调用带超时的tryLock方法reentrantLock.tryLock(longtimeout, TimeUnit unit)，
//那么如果线程在等待时被中断，将抛出一个InterruptedException异常，这是一个非常
//有用的特性，因为它允许程序打破死锁。你也可以调用reentrantLock.lockInterruptibly()方法，它就相当于一个超时设为无限的tryLock方法。
public class InterruptThreadTest5 {

    public void deathLock(Object lock1, Object lock2) {
        try {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName()+ " is running");
                // 让另外一个线程获得另一个锁
                Thread.sleep(10);
                // 造成死锁
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName());
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+ " is interrupted");
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {

        final InterruptThreadTest5 itt = new InterruptThreadTest5();
        final Object lock1 = new Object();
        final Object lock2 = new Object();
        Thread t1 = new Thread(new Runnable(){
            public void run() {
                itt.deathLock(lock1, lock2);
            }
        },"A");
        Thread t2 = new Thread(new Runnable(){
            public void run() {
                itt.deathLock(lock2, lock1);
            }
        },"B");

        t1.start();
        t2.start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 中断线程t1、t2
        t1.interrupt();
        t2.interrupt();
    }
}