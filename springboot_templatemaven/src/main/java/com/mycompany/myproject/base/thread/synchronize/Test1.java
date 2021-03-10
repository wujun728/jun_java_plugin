package com.mycompany.myproject.base.thread.synchronize;

public class Test1 {

    public static void main(String[] args){

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                synchronized(this){
                    for (int i = 0; i < 5; i++) {
                        System.out.println(Thread.currentThread().getName() + " synchronized loop " + i);
                    }
                }
            }
        };

        Thread thread1 = new Thread(runnable);
        Thread thread2 = new Thread(runnable);
        thread1.start();
        thread2.start();

    }

}
