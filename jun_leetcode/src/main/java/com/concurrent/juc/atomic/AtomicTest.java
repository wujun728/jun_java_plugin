package com.concurrent.juc.atomic;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 1.原子类
 * <p>
 * i++不是原子性操作,实际操作都是经过了读 写改三个步骤
 * int temp = i;
 * i = i + 1;
 * i = temp;
 * <p>
 * 原子变量：jdk1.5后 java.uti.concurrent.atomic 包下提供了原子变量
 * 1. volatile 保证内存可见性
 * 2. CAS算法（compare - and - swap） 算法保证数值的原子性
 * CAS算法是对硬件对于并发共享数据的支持
 * CAS包含了三个操作数：
 * 内存值：V
 * 预估值：A
 * 更新值：B
 * 当前仅当：V==A,B=V，否则将不做任何操作
 * 結果：控制只有一个线程操作数据
 *
 * @author BaoZhou
 * @date 2018/7/25
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicIntegerThread atomicDemo = new AtomicIntegerThread();
        //开启线程池（需要引入guava）
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();
        ExecutorService pool = new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            pool.execute(atomicDemo);
        }
        pool.shutdown();
    }
}





