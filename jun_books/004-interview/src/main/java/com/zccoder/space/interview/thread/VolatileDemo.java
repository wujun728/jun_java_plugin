package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * volatile 关键字示例
 *
 * @author zc
 * @date 2020/05/02
 */
public class VolatileDemo {

    public static void main(String[] args) {

        // 演示可见性
        seeOkByVolatile();

        // 共享资源
        MyData myData = new MyData();

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("volatile-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(20, 20, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 创建20个任务
        int total = 20;
        for (int i = 0; i < total; i++) {
            MyAddPlusPlusRunner runner = new MyAddPlusPlusRunner(myData);
            executor.execute(runner);
        }

        // 需要等待20个任务都全部计算完成后，再用main线程取得最终的结果值是多少
        while (true) {
            // 这里直接获取线程池中激活状态的线程个数
            if (executor.getActiveCount() > 0) {
                // 如果任务线程还未执行完毕，则main线程让出执行权
                Thread.yield();
            } else {
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + "\t int type finally number value：" + myData.getNumber());
        System.out.println(Thread.currentThread().getName() + "\t AtomicInteger finally number value：" + myData.getAtomicInteger().get());
        executor.shutdown();
    }

    /**
     * volatile 可以保证可见性，及时通知其他线程，主物理内存的值已经被修改
     */
    private static void seeOkByVolatile() {
        // 共享资源
        MyData myData = new MyData();

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("volatile-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 创建任务
        MyRunner myRunner = new MyRunner(myData);
        executor.execute(myRunner);

        while (true) {
            // main线程就一直在这里等待循环，直到number值不再等于零
            if (0 != myData.getNumber()) {
                break;
            }
        }

        System.out.println(Thread.currentThread().getName() + "\t mission is over，main get number value：" + myData.getNumber());

        // 关闭线程池
        executor.shutdown();
    }

}
