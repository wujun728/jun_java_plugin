package com.zccoder.space.interview.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * 实现Callable接口
 *
 * @author zc
 * @date 2020/05/04
 */
public class MyCallable implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + "\t come in call");
        // 休眠2秒，模拟实际耗时
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }
}