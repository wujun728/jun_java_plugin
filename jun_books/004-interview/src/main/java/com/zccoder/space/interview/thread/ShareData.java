package com.zccoder.space.interview.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 资源类
 *
 * @author zc
 * @date 2020/05/03
 */
class ShareData {

    /**
     * 变量
     */
    private int number;

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    void increment() {
        lock.lock();
        try {
            // 1.判断
            while (0 != number) {
                // 等待，不能生产
                condition.await();
            }
            // 2.干活
            number++;
            System.out.println(Thread.currentThread().getName() + "\t " + number);

            // 3.通知唤醒
            condition.signalAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void decrement() {
        lock.lock();
        try {
            // 1.判断
            while (0 == number) {
                // 等待，不能消费
                condition.await();
            }
            // 2.干活
            number--;
            System.out.println(Thread.currentThread().getName() + "\t " + number);

            // 3.通知唤醒
            condition.signalAll();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
