package com.mycompany.myproject.base.thread.interrupt;


//一般来说中断线程分为三种情况：
//(一) ：中断非阻塞线程
//(二)：中断阻塞线程
//(三)：不可中断线程

//(一) ：中断非阻塞线程
//中断非阻塞线程通常有两种方式：
//(1)采用线程共享变量
//   这种方式比较简单可行，需要注意的一点是共享变量必须设置为volatile，这样才能保证修改后其他线程立即可见。
public class InterruptThreadTest extends Thread{

    // 设置线程共享变量
    volatile boolean isStop = false;

    public void run() {
        while(!isStop) {
            long beginTime = System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName() + "is running");
            // 当前线程每隔一秒钟检测一次线程共享变量是否得到通知
            while (System.currentTimeMillis() - beginTime < 1000) {}
        }
        if (isStop) {
            System.out.println(Thread.currentThread().getName() + "is interrupted");
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        InterruptThreadTest itt = new InterruptThreadTest();
        itt.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // 线程共享变量设置为true
        itt.isStop = true;
    }

}
