package com.zccoder.java.book1.ch4.composing;

/**
 * 标题：内部锁演示<br>
 * 描述：内部锁是可重入的<br>
 * 时间：2018/10/24<br>
 *
 * @author zc
 **/
public class Widget {

    public synchronized void doSomething() {
        System.out.println("Do something!");
    }
}
