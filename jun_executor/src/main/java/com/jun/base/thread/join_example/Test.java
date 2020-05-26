package com.jun.base.thread.join_example;

public class Test {

    public static void main(String[] args) {
        // new Thread(){}; 创建了一个Thread类的匿名子类对象
        Thread t1 = new Thread("T1"){
            @Override
            public void run() {
               for(int i=0;i<1000;i++){
                   System.out.println(this.getName()+"-》"+i);
               }
            }
        };

        Thread t2 = new Thread(() -> {

                for(int i=0;i<1000;i++){
                    System.out.println(Thread.currentThread().getName()+"-》"+i);
                    if(i==200){
                        try {
                            t1.join();//t2调用了t1的join方法，导致t2阻塞直到t1结束。
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

        },"T2");

        t2.start();
        t1.start();

    }
}
