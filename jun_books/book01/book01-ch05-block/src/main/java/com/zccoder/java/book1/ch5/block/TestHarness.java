package com.zccoder.java.book1.ch5.block;

import java.util.concurrent.CountDownLatch;

/**
 * 标题：清单5.11 在时序测试中，使用CountDownLatch来启动和停止线程<br>
 * 描述：<br>
 * 时间：2018/10/26<br>
 *
 * @author zc
 **/
public class TestHarness {

    public long timeTask(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread thread = new Thread(() -> {
                try {
                    startGate.await();
                    try {
                        task.run();
                    } finally {
                        endGate.countDown();
                    }
                } catch (InterruptedException ex) {
                    // ignored
                }
            });
            thread.start();
        }

        long start = System.nanoTime();
        startGate.countDown();
        endGate.countDown();
        long end = System.nanoTime();
        return end - start;
    }

}
