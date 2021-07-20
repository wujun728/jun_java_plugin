package com.mycompany.myproject.base.concurrent.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorsTest1 {


    public static void main(String[] args){

        ExecutorService es = Executors.newFixedThreadPool(3, new MyThreadFactory());
        List<Future> list = new ArrayList<Future>();
        int j = 0;
        for(int i = 0; i < 20; i++){

            Future<String> future = es.submit(new Callable<String>() {

                public String call(){

                    return Thread.currentThread().getName();
                };

            });

            list.add(future);
        }

        list.forEach(future -> {
            try {
                System.out.println(future.get());

            }catch (InterruptedException iex){

            }catch (ExecutionException eex){

            }
        });

        //es.shutdown();
    }

    public static class MyThreadFactory implements ThreadFactory {

        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup group;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        MyThreadFactory() {
            SecurityManager s = System.getSecurityManager();
            group = (s != null) ? s.getThreadGroup() :
                    Thread.currentThread().getThreadGroup();
            namePrefix = "My-pool-" +
                    poolNumber.getAndIncrement() +
                    "-thread-";
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(group, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
            if (t.isDaemon())
                t.setDaemon(false);
            if (t.getPriority() != Thread.NORM_PRIORITY)
                t.setPriority(Thread.NORM_PRIORITY);
            return t;
        }
    }

}
