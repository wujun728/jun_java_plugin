package com.zccoder.java.book1.ch2.safety;

/**
 * 标题：子类<br>
 * 描述：子类<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
public class LoggingWidget extends Widget {

    @Override
    public synchronized void doSomething() {
        System.out.println("Logging Do something!");
        super.doSomething();
    }
}
