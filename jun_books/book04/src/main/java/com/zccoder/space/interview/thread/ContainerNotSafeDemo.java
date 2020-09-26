package com.zccoder.space.interview.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 容器线程不安全示例
 *
 * @author zc
 * @date 2020/05/02
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        Map<String, String> map = new ConcurrentHashMap<>(16);

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("HashMap-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(40, 40, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 创建40个任务
        int total = 40;
        for (int i = 0; i < total; i++) {
            executor.execute(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            });
        }

        // 关闭线程池
        executor.shutdown();
    }

    private static void setNotSafe() {
        Set<String> set = new CopyOnWriteArraySet<>();

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("set-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(35, 35, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 创建35个任务
        int total = 35;
        for (int i = 0; i < total; i++) {
            executor.execute(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            });
        }

        // 关闭线程池
        executor.shutdown();
    }

    private static void listNotSafe() {
        // 使用CopyOnWriteArrayList
        List<String> list = new CopyOnWriteArrayList<>();

        // 创建线程池
        BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(1024);
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("array-list-demo-pool-%d").build();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 30, 0, TimeUnit.SECONDS, queue, threadFactory);

        // 创建30个任务
        int total = 30;
        for (int i = 0; i < total; i++) {
            executor.execute(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            });
        }

        // 关闭线程池
        executor.shutdown();
    }
}
