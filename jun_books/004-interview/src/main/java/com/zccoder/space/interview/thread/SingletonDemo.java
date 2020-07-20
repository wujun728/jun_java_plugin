package com.zccoder.space.interview.thread;

/**
 * 单例模式
 *
 * @author zc
 * @date 2020/05/02
 */
public class SingletonDemo {

    /**
     * 使用volatile修饰，禁止指令重排
     */
    private static volatile SingletonDemo instance = null;

    private SingletonDemo() {
        System.out.println(Thread.currentThread().getName() + "\t 我是构造方法SingletonDemo()");
    }

    /**
     * 可以在方法上加synchronized修饰，但锁的范围太大
     */
    private static SingletonDemo getInstance() {
        if (instance == null) {
            synchronized (SingletonDemo.class) {
                // 使用双检锁机制（Double Check Lock）
                if (instance == null) {
                    instance = new SingletonDemo();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        // 并发线程后，情况发生了很大的变化
        int total = 10;
        for (int i = 0; i < total; i++) {
            // 这里为了演示效果，使用new创建线程。为了使P3C扫描通过，屏蔽处理。需要执行查看效果时，取消屏蔽即可
            // new Thread(() -> SingletonDemo.getInstance().equals(SingletonDemo.getInstance()), String.format("singleton-demo-thread-%d", i)).start();
        }
    }
}
