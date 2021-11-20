package com.mycompany.myproject.base.concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest {

    public static void main(String[] args){

        int taskCount = 10;

        CountDownLatch countDownLatch = new CountDownLatch(taskCount);

        for( int i = 0; i < taskCount; i++){

            new Thread(){

                public void run(){
                    System.out.println("线程" + Thread.currentThread().getId() + "开始出发");
                    try{
                        //延时5秒
//                        Thread.sleep(5 * 1000);
//                        Thread.sleep(TimeUnit.SECONDS.toMillis(30));
                        TimeUnit.SECONDS.sleep( 5 );

                    }catch (Exception ex){
                        ex.printStackTrace();
                    }finally {

                        System.out.println("线程" + Thread.currentThread().getId() + "已到达终点");
                        countDownLatch.countDown();
                    }

                }

            }.start();
        }


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("10个线程已经执行完毕！ 开始计算排名");
    }


}
