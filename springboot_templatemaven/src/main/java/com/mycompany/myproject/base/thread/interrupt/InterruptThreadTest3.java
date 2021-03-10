package com.mycompany.myproject.base.thread.interrupt;

// 中断阻塞线程
// 当线程调用Thread.sleep()、Thread.join()、object.wait()再或者调用阻塞的i/o操作方法时，都会使得当前线程进入阻塞状态。
// 那么此时如果在线程处于阻塞状态是调用interrupt() 方法设置线程中断标志位时会出现什么情况呢！
// 此时处于阻塞状态的线程会抛出一个异常，并且会清除线程中断标志位(设置为false)。这样一来线程就能退出
// 阻塞状态。当然抛出异常的方法就是造成线程处于阻塞状态的Thread.sleep()、Thread.join()、object.wait()这些方法。
public class InterruptThreadTest3 extends Thread{

    public void run() {
        // 这里调用的是非清除中断标志位的isInterrupted方法
        while(!Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + " is running");
            try {
                System.out.println(Thread.currentThread().getName() + " Thread.sleep begin");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " Thread.sleep end");
            } catch (InterruptedException e) {
                //需要注意的地方就是 Thread.sleep()、Thread.join()、object.wait()这些方法，会检测线程中断标志位，
                // 如果发现中断标志位为true则抛出异常并且将中断标志位设置为false。
                //所以while循环之后每次调用阻塞方法后 都要在捕获异常之后，调用Thread.currentThread().interrupt()重置状态标志位。
                // TODO Auto-generated catch block
                //由于调用sleep()方法清除状态标志位 所以这里需要再次重置中断标志位 否则线程会继续运行下去
                Thread.currentThread().interrupt();
                e.printStackTrace();
            }
        }
        if (Thread.currentThread().isInterrupted()) {
            System.out.println(Thread.currentThread().getName() + "is interrupted");
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        InterruptThreadTest3 itt = new InterruptThreadTest3();
        itt.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 设置线程的中断标志位
        itt.interrupt();
    }
}