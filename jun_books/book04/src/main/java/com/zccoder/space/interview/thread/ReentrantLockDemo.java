package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 可重入锁示例
 *
 * @author zc
 * @date 2020/05/03
 */
public class ReentrantLockDemo {

    public static void main(String[] args) {
        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ReentrantLock-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(4, 4, 0, TimeUnit.SECONDS, queue, threadFactory);

        Phone phone = new Phone();
        // 线程一
        executor.execute(phone::sendSms);
        // 线程二
        executor.execute(phone::sendSms);

        // main线程休眠2秒，让synchronized方式执行完毕后，再执行ReentrantLock方式
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();

        PhoneRunner phoneRunner = new PhoneRunner();
        // 线程三
        executor.execute(phoneRunner);
        // 线程四
        executor.execute(phoneRunner);

        // 关闭线程池
        executor.shutdown();
    }
}
