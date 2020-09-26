package com.zccoder.space.interview.thread;

import java.util.concurrent.TimeUnit;

/**
 * 死锁示例资源类
 *
 * @author zc
 * @date 2020/05/04
 */
public class DeadLockHold implements Runnable {

    private final String lockA;
    private final String lockB;

    DeadLockHold(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockA + "\t 尝试获取：" + lockB);
            // 线程休眠2秒，便于查看效果
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + "\t 自己持有：" + lockB + "\t 尝试获取：" + lockA);
            }
        }
    }
}
