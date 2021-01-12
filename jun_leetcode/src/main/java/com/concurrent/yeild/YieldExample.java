package com.concurrent.yeild;


/**
 * @author BaoZhou
 * @date 2019/8/26
 */
public class YieldExample {
    public static void main(String[] args) {
        ThreadA threadA = new ThreadA("A");
        ThreadA threadB = new ThreadA("B");
        threadA.start();
        threadB.start();
    }
}


class ThreadA extends Thread {
    public ThreadA(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 1; i <= 50; i++) {
            System.out.println(this.getName() +" : "+ i);
            // 当i为30时，该线程就会把CPU时间让掉，让其他或者自己的线程执行（也就是谁先抢到谁执行）
            if (i == 30) {
                Thread.yield();
            }
        }
    }
}