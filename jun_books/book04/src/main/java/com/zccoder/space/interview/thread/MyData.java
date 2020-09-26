package com.zccoder.space.interview.thread;

import java.util.concurrent.atomic.AtomicInteger;

import lombok.Data;

/**
 * 被多个线程共享访问的数据
 *
 * @author zc
 * @date 2020/05/02
 */
@Data
class MyData {

    /**
     * 共享变量；当没有volatile修饰时，不具备可见性
     */
    private volatile int number = 0;

    void addTo60() {
        // 某个线程读取 number 值为0，并修改为60，然后将值写会主内存
        this.number = 60;
    }

    /**
     * 保证原型性方式一：使用synchronized修饰方法，但性能较低
     */
    synchronized void addPlusPlus() {
        // 此时已经被volatile修饰，但是不保证原子性。i++操作不具备原子性
        this.number++;
    }

    /**
     * 保证原型性方式二：使用JUC提供的原子类AtomicInteger
     */
    private AtomicInteger atomicInteger = new AtomicInteger();

    void addAtomic() {
        // 为什么AtomicInteger能保证原子性，因为底层使用了 CAS 机制
        this.atomicInteger.getAndIncrement();
    }

}
