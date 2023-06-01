package com.mycompany.myproject.base.thread;

import java.util.Arrays;

public class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }


    @Override
    public void run(){

        System.out.println("子线程ID:"+Thread.currentThread().getId() + " Name: " + Thread.currentThread().getName());
    }

    public static void main(String[] args){
        System.out.println("主线程ID:"+Thread.currentThread().getId() + " Name: " + Thread.currentThread().getName());

        // 直接调用run 方法， 和普通方法无异
        new MyThread("MyThread").run();

        Arrays.stream(new Integer[]{0,1,2,3,4,5,6,7,8,9}).forEach(i -> {
            MyThread myThread = new MyThread("MyThread" + String.valueOf(i));
            myThread.start();
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
