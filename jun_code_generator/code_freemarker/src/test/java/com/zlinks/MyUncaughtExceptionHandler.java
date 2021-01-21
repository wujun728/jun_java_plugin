package com.zlinks;

/**
 * Copyright (C), 2018-2018, BBG
 * FileName: MyUncaughtExceptionHandler
 * Author: Wujun
 * Date: 2018/7/7 下午4:47
 * Description:
 */
public class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println("\n [caugth:] " + e.toString());
    }

}