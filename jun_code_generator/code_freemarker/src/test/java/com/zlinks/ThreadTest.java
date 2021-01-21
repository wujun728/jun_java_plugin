package com.zlinks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright (C), 2018-2018, BBG
 * FileName: ThreadTest
 * Author: Wujun
 * Date: 2018/7/7 下午4:38
 * Description:
 */
public class ThreadTest extends Thread {

    @Override
    public void run() {
        System.out.println("1");
//        try {
        if (1 > 0) {
            throw new RuntimeException();
        }

//        } catch (Exception e) {
//            System.out.println("2");
//        } finally {
//            System.out.println("3");
//        }
        System.out.println("4");
    }


    public static void main(String[] args) {
        try {
            ThreadTest threadTest =  new ThreadTest();
            threadTest.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
            ExecutorService service = Executors.newCachedThreadPool();
            service.execute(threadTest);
        } catch (Exception e) {
            System.out.println("我捕捉到异常了");
            e.printStackTrace();
        }
    }
}
