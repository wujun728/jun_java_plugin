package com.jun.base.threadTest.atomic;

import com.sun.org.apache.xpath.internal.SourceTree;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class IntLongBoo {

    static AtomicInteger ai = new AtomicInteger(0);
    static StringBuffer s = new StringBuffer();
    //static StringBuilder s = new StringBuilder();
    public static void main(String[] args) {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);


        for(int i = 0; i <=15; i++){

            final int j = i;
            fixedThreadPool.execute(new Runnable() {

                @Override
                public void run() {
                    try {

                        ai.getAndIncrement();
                        System.err.println(ai.get());
                        //ai.getAndIncrement();//后执行就会出现多个0   ai.get()方法没有加锁
                        s.append(j+"-");
                        //s.append(j);
                        System.out.println("------------------="+s.toString());

                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }


    }



}
