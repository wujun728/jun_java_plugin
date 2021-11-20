package com.jun.base.threadTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {


   static   Boolean is = true;
    public static void main(String[] args) {

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(8);
        for (int i = 0; i < 12; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {


                @Override
                public void run() {
                  a();
                }
            });
        }

    }


    public static void a(){

        if(is){

            System.out.println("执行方法");
            is= false;

        }

    }


}
