package com.mycompany.myproject.base.concurrent.executors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class ExecutorsTest2 {

    public static void main(String[] args){

        ExecutorService es =  Executors.newCachedThreadPool();

//        Task task = new Task();
//        FutureTask<Integer> futureTask = new FutureTask<Integer>(task);
//
//
//        es.submit(futureTask);

        Task task1 = new  Task();
        es.submit(task1);

    }

    public static class Task implements Callable<Integer> {
        @Override
        public Integer call() throws Exception {
            System.out.println("子线程在进行计算");
            Thread.sleep(3000);
            int sum = 0;
            for (int i = 0; i < 100; i++)
                sum += i;
            return sum;
        }
    }
}
