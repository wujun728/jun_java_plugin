package com.jun.base.thread.yield_example;

public class TestYield {

    public static void main(String[] args) {
        Thread t1 = new Thread("T1"){
            @Override
            public void run() {
                for(int i=0;i<1000;i++){
                    System.out.println(this.getName()+"-》"+i);
                    Thread.yield();
                }
            }
        };

        Thread t2 = new Thread(() -> {

            for(int i=0;i<1000;i++){
               System.out.println(Thread.currentThread().getName()+"-》"+i);
               Thread.yield();
            }

        },"T2");

        t2.start();
        t1.start();

    }
}
