package com.mycompany.myproject.base.thread.sleep;

import java.io.IOException;

// sleep方法不会释放锁，也就是说如果当前线程持有对某个对象的锁，则即使调用sleep方法，其他线程也无法访问这个对象。
// 如果调用了sleep方法，必须捕获InterruptedException异常或者将该异常向上层抛出。
// 当线程睡眠时间满后，不一定会立即得到执行，因为此时可能CPU正在执行其他的任务。所以说调用sleep方法相当于让线程进入阻塞状态
public class SleepTest {
    private int i = 10;
    private Object object = new Object();

    public static void main(String[] args) throws IOException {
        SleepTest test = new SleepTest();
        MyThread thread1 = test.new MyThread();
        MyThread thread2 = test.new MyThread();
        thread1.start();
        thread2.start();
    }


    class MyThread extends Thread{
        @Override
        public void run() {
            synchronized (object) {
                i++;
                System.out.println("i:"+i);
                try {
                    System.out.println("线程"+Thread.currentThread().getName()+"进入睡眠状态");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                }
                System.out.println("线程"+Thread.currentThread().getName()+"睡眠结束");
                i++;
                System.out.println("i:"+i);
            }
        }
    }
}
