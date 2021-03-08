package com.mycompany.myproject.base.concurrent.cyclicBarrier;

import java.util.concurrent.*;

public class CyclicBarrierTest3 {

    public static void main(String[] args) throws  Exception{


        CyclicBarrier barrier1 = new CyclicBarrier(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        //添加一个用await()等待的线程
        executorService.submit(() -> {
            try {
                //等待，除非：
                // 1.屏障打开;
                // 2.本线程被interrupt;
                // 3.其他等待线程被interrupted;
                // 4.其他等待线程timeout;
                // 5.其他线程调用reset()
                barrier1.await();
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " is interrupted.");
                //e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " is been broken.");
                e.printStackTrace();
            }
        });
        Thread.sleep(10);
        System.out.println("刚开始，屏障是否破损：" + barrier1.isBroken());

        //添加一个等待线程-并超时
        executorService.submit(() -> {
            try {
                //等待1s，除非：1.屏障打开(返回true);2.本线程被interrupt;
                // 3.本线程timeout;
                // 4.其他等待线程被interrupted;
                // 5.其他等待线程timeout;6.其他线程调用reset()
                barrier1.await(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " is interrupted.");
                //e.printStackTrace();
            } catch (BrokenBarrierException e) {
                System.out.println(Thread.currentThread().getName() + " is been reset().");
                //e.printStackTrace();
            } catch (TimeoutException e) {
                System.out.println(Thread.currentThread().getName() + " is timeout.");
                //e.printStackTrace();
            }
        });
        Thread.sleep(100);
        System.out.println("当前等待线程数量：" + barrier1.getNumberWaiting());
        Thread.sleep(1000);
        System.out.println("当前等待线程数量：" + barrier1.getNumberWaiting());
        System.out.println("当等待的线程timeout时，当前屏障是否破损：" + barrier1.isBroken());
        System.out.println("等待的线程中，如果有一个出现问题，则此线程会抛出相应的异常；" +
                "其他线程都会抛出BrokenBarrierException异常。");

        System.out.println();
        Thread.sleep(5000);
        //通过reset()重置屏障回初始状态，也包括是否破损
        barrier1.reset();
        System.out.println("reset()之后，当前屏障是否破损：" + barrier1.isBroken());
        System.out.println("reset()之后，当前等待线程数量：" + barrier1.getNumberWaiting());
        executorService.shutdown();
    }
}
