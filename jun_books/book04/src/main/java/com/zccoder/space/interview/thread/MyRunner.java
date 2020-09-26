package com.zccoder.space.interview.thread;

import java.util.concurrent.TimeUnit;

/**
 * 执行任务
 *
 * @author zc
 * @date 2020/05/02
 */
public class MyRunner implements Runnable {

    private MyData myData;

    MyRunner(MyData myData) {
        this.myData = myData;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "\t come in");
        try {
            // 线程休眠3秒
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.myData.addTo60();

        System.out.println(Thread.currentThread().getName() + "\t update number value：" + myData.getNumber());
    }
}
