package com.zccoder.space.interview.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共享资源类
 *
 * @author zc
 * @date 2020/05/03
 */
class MyPrint {

    private int a = 1;
    private int b = 2;
    private int c = 3;

    private int nextPoint = 1;
    private ReentrantLock lock = new ReentrantLock();

    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    void printA(int markCount) {
        lock.lock();
        try {
            while (nextPoint != a) {
                condition1.await();
            }
            // 打印5次
            int total = 5;
            for (int i = 1; i <= total; i++) {
                System.out.println(Thread.currentThread().getName() + " \t 第" + markCount + "轮的" + i + "个  AA");
            }
            nextPoint = b;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void printB(int markCount) {
        lock.lock();
        try {
            while (nextPoint != b) {
                condition2.await();
            }
            // 打印10次
            int total = 10;
            for (int i = 1; i <= total; i++) {
                System.out.println(Thread.currentThread().getName() + " \t 第" + markCount + "轮的" + i + "个  BB");
            }
            nextPoint = c;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void printC(int markCount) {
        lock.lock();
        try {
            while (nextPoint != c) {
                condition3.await();
            }
            // 打印15次
            int total = 15;
            for (int i = 1; i <= total; i++) {
                System.out.println(Thread.currentThread().getName() + " \t 第" + markCount + "轮的" + i + "个  CC");
            }
            nextPoint = a;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
