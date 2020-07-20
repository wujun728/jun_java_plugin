package com.zccoder.java.book1.ch2.safety;

import com.zccoder.java.book1.ch0.basic.ThreadSafe;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 标题：线程安全的计数<br>
 * 描述：使用已有的线程安全类<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
@ThreadSafe
public class StatelessFactorizer {

    /**
     * 使用已有的线程安全类
     * java.util.concurrent.atomic包中包括了原子变量类
     */
    private final AtomicLong count = new AtomicLong(0);

    public void service(String name) {
        // 为了确保线程安全，自增是“读-改-写”操作必须是原子操作，我们将“读-改-写”的全部执行过程看做是复合操作
        // 为了保证线程安全，复合操作必须原子地执行。后面会使用Java内置的原子性机制：锁。
        count.incrementAndGet();
        System.out.println("print name :" + name);
    }

    public long getCount() {
        return count.get();
    }
}
