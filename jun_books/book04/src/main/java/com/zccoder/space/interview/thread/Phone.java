package com.zccoder.space.interview.thread;

/**
 * 线程操作资源类
 *
 * @author zc
 * @date 2020/05/03
 */
public class Phone {

    synchronized void sendSms() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendSms()");
        sendEmail();
    }

    synchronized void sendEmail() {
        System.out.println(Thread.currentThread().getName() + "\t invoked sendEmail()");
    }

}