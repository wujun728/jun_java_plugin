package com.mycompany.myproject.base.concurrent.cyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierTest4 {
    
    public static void main(String[] args){

        //构造器：设置屏障放开前做的事情
        CyclicBarrier barrier3 = new CyclicBarrier(2, () -> {
            System.out.println("屏障放开，[屏障线程]先运行！");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[屏障线程]的事情做完了!");
        });

        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + " 等待屏障放开");
                try {
                    barrier3.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "开始干活...干活结束");
            }).start();
        }
        
    }
}
