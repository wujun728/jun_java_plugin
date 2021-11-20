package com.demo.threadpool;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.jun.base.executor.threadpool.ThreadPoolDemo;

import java.util.concurrent.ExecutionException;

/**
 * 描述: 测试
 *
 * @author Wujun
 * @date : 2020-01-28 15:14
 */
public class ThreadPoolDemoTest {
    private static ThreadPoolDemo threadPoolDemo = new ThreadPoolDemo();

    /**
     * ES 客户端的创建：获取Rest高级客户端
     */
    @BeforeEach
    void init() {

    }

    /**
     * 创建2个线程的fixedThreadPool ，当2个都为活跃的时候，
     * 后面的任务会被加入链式队列（LinkedBlockingQueue）
     *
     * @throws ExecutionException   执行异常
     * @throws InterruptedException 中断异常
     */
    @Test
    void fixedThreadPool() throws ExecutionException, InterruptedException {
        threadPoolDemo.fixedThreadPool(2);
    }

    @Test
    void cacheThreadPool() throws ExecutionException, InterruptedException {
        threadPoolDemo.cacheThreadPool();
    }

    /**
     * 定时定期的线程池不能使用单元测试，好像不能执行？
     *
     * @param args 主函数
     */
    public static void main(String[] args) {
        threadPoolDemo.scheduledThreadPool();
    }

    @Test
    void singleThreadPool() throws ExecutionException, InterruptedException {
        threadPoolDemo.singleThreadPool();
    }


    @AfterEach
    void shutdown() {
    }

}
