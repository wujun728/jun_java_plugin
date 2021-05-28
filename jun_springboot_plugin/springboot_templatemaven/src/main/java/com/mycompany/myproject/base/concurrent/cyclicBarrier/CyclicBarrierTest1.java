package com.mycompany.myproject.base.concurrent.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest1 {
    
    
    public static void main(String[] args) throws Exception{
        
        //构造函数1：初始化-开启屏障的方数
        CyclicBarrier barrier0 = new CyclicBarrier(2);
        //通过barrier.getParties()获取开启屏障的方数
        System.out.println("barrier.getParties()获取开启屏障的方数：" + barrier0.getParties());
        System.out.println();
        //通过barrier.getNumberWaiting()获取正在等待的线程数
        System.out.println("通过barrier.getNumberWaiting()获取正在等待的线程数：初始----"
                + barrier0.getNumberWaiting());
        System.out.println();
        
        new Thread(() -> {
            //添加一个等待线程
            System.out.println("添加第1个等待线程----" + Thread.currentThread().getName());
            try {
                barrier0.await();
                System.out.println(Thread.currentThread().getName() + " is running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is terminated.");
        }).start();
        
        Thread.sleep(10);
        //通过barrier.getNumberWaiting()获取正在等待的线程数
        System.out.println("通过barrier.getNumberWaiting()获取正在等待的线程数：添加第1个等待线程---"
                + barrier0.getNumberWaiting());
        Thread.sleep(10);
        System.out.println();
        new Thread(() -> {
            //添加一个等待线程
            System.out.println("添加第2个等待线程----" + Thread.currentThread().getName());
            try {
                barrier0.await();
                System.out.println(Thread.currentThread().getName() + " is running...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is terminated.");
        }).start();
        Thread.sleep(100);
        System.out.println();
        //通过barrier.getNumberWaiting()获取正在等待的线程数
        System.out.println("通过barrier.getNumberWaiting()获取正在等待的线程数：打开屏障之后---"
                + barrier0.getNumberWaiting());

        //已经打开的屏障，再次有线程等待的话，还会重新生效--视为循环
        new Thread(() -> {
            System.out.println("屏障打开之后，再有线程加入等待：" + Thread.currentThread().getName());
            try {
                //BrokenBarrierException
                barrier0.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is terminated.");

        }).start();
        System.out.println();
        Thread.sleep(10);
        System.out.println("通过barrier.getNumberWaiting()获取正在等待的线程数：打开屏障之后---"
                + barrier0.getNumberWaiting());
        Thread.sleep(10);

        new Thread(() -> {
            System.out.println("屏障打开之后，再有线程加入等待：" + Thread.currentThread().getName());
            try {
                //BrokenBarrierException
                barrier0.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " is terminated.");

        }).start();

        Thread.sleep(10);
        System.out.println("通过barrier.getNumberWaiting()获取正在等待的线程数：打开屏障之后---"
                + barrier0.getNumberWaiting());
    }
    
}
