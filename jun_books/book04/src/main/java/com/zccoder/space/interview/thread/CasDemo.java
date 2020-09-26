package com.zccoder.space.interview.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 机制示例。CAS 比较并交换。
 *
 * @author zc
 * @date 2020/05/02
 */
public class CasDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        // 如果线程的期望值跟物理内存的真实值一样，就修改为更新值，本次修改成功
        boolean b1 = atomicInteger.compareAndSet(5, 2020);
        System.out.println("结果：" + b1 + " \t current data：" + atomicInteger.get());

        // 如果线程的期望值跟物理内存的真实值不一样，本次修改失败，这时需要重新获取物理内存的真实值
        boolean b2 = atomicInteger.compareAndSet(5, 1024);
        System.out.println("结果：" + b2 + " \t current data：" + atomicInteger.get());
    }

}
