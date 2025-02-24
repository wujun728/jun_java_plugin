package com.mycompany.myproject.base.thread;


import java.util.Arrays;
import java.util.List;

public class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("子线程ID:"+Thread.currentThread().getId() + " Name: " + Thread.currentThread().getName());
    }

    public static void main(String[] args){
        System.out.println("主线程ID:"+Thread.currentThread().getId() + " Name: " + Thread.currentThread().getName());

        // 直接调用run 方法， 和普通方法无异
        new MyRunnable().run();

        List<Integer> list = Arrays.asList(0,1,2,3,4,5,6,7,8,9);

        list.stream().mapToInt((x) -> x).forEach(i -> {


            //Thread thread = new Thread(new MyRunnable(), "MyRunnable" + String.valueOf(i));

            // lambda 表达式方式 创建
            Thread thread = new Thread(()->{
                System.out.println("子线程ID:"+Thread.currentThread().getId()
                        + " Name: " + Thread.currentThread().getName());
            }, "MyRunnable" + String.valueOf(i));

            thread.start();

            if(i%2 == 0){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                }
            }
        });

        System.out.println("main  finished");
    }
}
