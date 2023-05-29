package com.jun.base.thread;

public class Main {

    public static void main(String[] args) {
	    Thread t0 = Thread.currentThread();//获取当前线程对象
        System.out.println("主线程的名称："+t0.getName());

        Thread t1 = new One("One");

        /**
         * 第二种创建线程的方式：以Runnable接口对象为构造参数直接创建Thread类对象
         */
        Thread t2 = new Thread(new Two(),"Two");

        //t1.run();//error!!!
        t1.start();//启动线程，start方法的作用就是将被调用start方法的线程放入线程池中
        t2.start();

        for(int i=1;i<=1000;i++){
            System.out.println(t0.getName()+":"+i);
            /*try {
                Thread.sleep(50);//当前线程睡眠50ms
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }
        System.out.println(t0.getName()+"结束！");
    }
}
