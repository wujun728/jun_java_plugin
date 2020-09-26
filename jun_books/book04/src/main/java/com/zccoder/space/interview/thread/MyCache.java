package com.zccoder.space.interview.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁资源类
 *
 * @author zc
 * @date 2020/05/03
 */
class MyCache {

    private volatile Map<String, Object> map = new HashMap<>(16);
    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    /**
     * 写操作需要保证：原子+独占；即写操作独占不可中断。整个过程必须是一个完整的统一体，中间不许被分割，被打断
     */
    void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入：" + key);
            // 线程休眠300毫秒，模拟网络拥堵
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + "\t 写入完成：" + value);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取：" + key);
            // 线程休眠300毫秒，模拟网络拥堵
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object value = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取完成：" + value);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}
