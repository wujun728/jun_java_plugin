package com.concurrent.juc.future;

import java.util.concurrent.*;

/**
 * 创建线程的方式：实现Callable 接口
 *
 * @author: BaoZhou
 * @date : 2018/7/26 21:37
 */
public class CallableExample {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ThreadDemo td = new ThreadDemo();
        FutureTask<Integer> result = new FutureTask<Integer>(td);
        for (int i = 0; i < 1; i++) {
            executorService.execute(result);
        }
        executorService.shutdown();
        try {
            Integer ok = result.get(); //也可以用于闭锁
            System.out.println("------------------------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

class ThreadDemo implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        int sum = 0;
        for (int i = 0; i < Integer.MAX_VALUE ; i++) {
            sum += i;
        }
        System.out.println(sum);
        return sum;
    }
}